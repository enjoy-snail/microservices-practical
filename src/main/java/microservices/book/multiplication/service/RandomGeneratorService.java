package microservices.book.multiplication.service;

/**
 * @author weitao
 * 2020-01-11 下午 14:16
 */
public interface RandomGeneratorService {
	/**
	 * @return a randomly-generated factor. It's always a number between 11 and 99.
	 */
	int generateRandomFactor();
}
