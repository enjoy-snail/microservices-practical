package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author weitao
 * 2020-01-11 下午 15:13
 */
public class MultiplicationServiceImplTest {

	private MultiplicationServiceImpl multiplicationServiceImpl;

	/**
	 * 没有使用 @MockBean注入 mock bean, 而使用了普通的 @Mock 注解来创建 mock service。
	 * 这样可以通过编程方式来构造 MultiplicationServiceImpl对象。
	 */
	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Before
	public void setUp() throws Exception {
		// With this call to initMocks we tell Mockito to process the annotations.
		MockitoAnnotations.initMocks(this);
		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
	}

	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		// when
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		//assertThat(multiplication.getResult()).isEqualTo(1500);
	}

	@Test
	public void checkCorrectAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// assert
		assertThat(attemptResult).isTrue();
	}

	@Test
	public void checkWrongAttemptTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// assert
		assertThat(attemptResult).isFalse();
	}
}
