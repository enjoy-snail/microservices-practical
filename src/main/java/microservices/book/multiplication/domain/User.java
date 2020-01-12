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
 * 2020-01-11 下午 18:12
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class User {

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;

	private final String alias;

	// Empty constructor for JSON (re)serialization / JPA
	protected User() {
		alias = null;
	}
}
