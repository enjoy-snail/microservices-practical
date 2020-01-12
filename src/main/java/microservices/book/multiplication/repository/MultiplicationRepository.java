package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

/**
 * @author weitao
 * 2020-01-12 下午 18:38
 */
public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
