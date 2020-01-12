package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author weitao
 * 2020-01-11 下午 14:34
 */

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;
	private MultiplicationResultAttemptRepository attemptRepository;
	private UserRepository userRepository;

	@Autowired
	public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
									 final MultiplicationResultAttemptRepository attemptRepository,
									 final UserRepository userRepository) {
		this.randomGeneratorService = randomGeneratorService;
		this.attemptRepository = attemptRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Transactional
	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
		//System.out.println(attempt.toString());

		// Check if the user already exists for that alias
		Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

		// Avoids 'hack' attempts
		// org.springframework.util.Assert：使用 Spring 所包含的 Assert，断言会触发 IllegalArgumentException 异常
		Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

		// Check if the attemp is correct
		boolean isCorrect =
				attempt.getResultAttempt() == attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();

		// Creates a copy, now setting the 'correct' field accordingly
		// 为了保持类的不可变性，此处我们需要创建一个对象的副本。
		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()),
				attempt.getMultiplication(),
				attempt.getResultAttempt(),
				isCorrect);

		// Stores the attempt
		// CascadeType.PERSIST 实体上使用了后，在保存 attempt 的同时，都会同时持久化与之关联的 Multiplication 和 User 对象
		attemptRepository.save(checkedAttempt);

		return isCorrect;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}
}
