package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author weitao
 * 2020-01-11 下午 18:14
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private final User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "MULTIPLICATION_ID")
	private final Multiplication multiplication;

	private final int resultAttempt;

	private final boolean correct;

	// Empty constructor for JSON (re)serialization / JPA
	protected MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}
}
