package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author weitao
 * 2020-01-12 下午 18:30
 */
public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {
	/**
	 * @return the latest 5 attempts for a given user indentified by their alias.
	 */
	List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
