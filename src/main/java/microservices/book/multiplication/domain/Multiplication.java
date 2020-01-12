package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author weitao
 * 2020-01-11 下午 14:10
 * This represents a Multiplication(a * b).
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {
	// Both factors
	private final int factorA;
	private final int factorB;

	// Empty constructor for JSON (re)serialization
	Multiplication() {
		this(0, 0);
	}
}
