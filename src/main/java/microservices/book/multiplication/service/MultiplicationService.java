package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

/**
 * @author weitao
 * 2020-01-11 下午 14:13
 */
public interface MultiplicationService {
	/**
	 * Generates a random {@link Multiplication} object.
	 * @return a multiplication of randomly generated numbers
	 */
	Multiplication createRandomMultiplication();

	/**
	 *
	 * @param resultAttempt
	 * @return true if the attempt matches the result of the multiplication, false otherwise.
	 */
	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

	List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}
