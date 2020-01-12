package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author weitao
 * 2020-01-11 下午 18:12
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class User {
	private final String alias;

	// Empty constructor for JSON (re)serialization
	protected User() {
		alias = null;
	}
}
