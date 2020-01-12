package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author weitao
 * 2020-01-11 下午 18:14
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class MultiplicationResultAttempt {

	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;

	// Empty constructor for JSON (re)serialization
	public MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
	}
}
