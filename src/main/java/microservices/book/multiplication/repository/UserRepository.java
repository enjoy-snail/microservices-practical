package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author weitao
 * 2020-01-12 下午 18:36
 */
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByAlias(final String alias);
}
