package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author weitao
 * 2020-01-11 下午 14:10
 * This represents a Multiplication(a * b).
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {

	@Id
	@GeneratedValue
	@Column(name = "MULTIPLICATION_ID")
	private Long id;
	// Both factors
	private final int factorA;
	private final int factorB;

	// Empty constructor for JSON (re)serialization / JPA
	protected Multiplication() {
		this(0, 0);
	}
}
