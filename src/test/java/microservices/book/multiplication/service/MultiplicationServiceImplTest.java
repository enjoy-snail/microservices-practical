package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author weitao
 * 2020-01-11 下午 15:13
 */
public class MultiplicationServiceImplTest {

	private MultiplicationServiceImpl multiplicationServiceImpl;

	/**
	 * 没有使用 @MockBean注入 mock bean, 而使用了普通的 @Mock 注解来创建 mock service。
	 * 这样可以通过编程方式来构造 MultiplicationServiceImpl对象。
	 */
	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;

	@Mock
	private UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		// With this call to initMocks we tell Mockito to process the annotations.
		MockitoAnnotations.initMocks(this);
		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository);
	}

	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		// when
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		//practical v2
		//assertThat(multiplication.getResult()).isEqualTo(1500);
	}

	@Test
	public void checkCorrectAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("john_doe"))
				.willReturn(Optional.empty());

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// assert
		assertThat(attemptResult).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
	}

	@Test
	public void checkWrongAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		given(userRepository.findByAlias("john_doe"))
				.willReturn(Optional.empty());

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// assert
		assertThat(attemptResult).isFalse();
		verify(attemptRepository).save(attempt);
	}

	@Test
	public void retrieveStatsTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
		ArrayList<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
		given(userRepository.findByAlias("john_doe"))
				.willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe"))
				.willReturn(latestAttempts);

		// when
		List<MultiplicationResultAttempt> latestAttemptResult = multiplicationServiceImpl.getStatsForUser("john_doe");

		// then
		assertThat(latestAttemptResult).isEqualTo(latestAttempts);
	}
}
