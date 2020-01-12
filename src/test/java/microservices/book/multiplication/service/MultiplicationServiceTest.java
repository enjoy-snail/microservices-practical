package microservices.book.multiplication.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weitao
 * 2020-01-11 下午 14:18
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceTest {

	/**
	 * TDD (Test-Driven Development) : 测试驱动开发的基本思想是，在编写主要业务代码逻辑前先写测试。首先让测试失败，然后编写代码让测试通过。
	 * 						TDD 要点 : 先编写规格说明，接着与业务分析师（产品负责人）验证，然后列出其他应该被覆盖的用例。这些都与解决方案的实现无关。一旦澄清了测试（需求），就可以编写解决方案（实现类）
	 * BDD (Behavior-Deiven Development) : 行为驱动开发
	 * @MockBean : 它让 Spring 注入一个 RandomGeneratorService bean 的一个 mock,
	 * 			   而不是搜寻这个接口的一个合适实现（目前还不存在实现 generateRandomFactor）
	 */
	@MockBean
	private RandomGeneratorService randomGeneratorService;

	@Autowired
	private MultiplicationService multiplicationService;

	/*
	practical : microservices-v2
	@Test
	public void createRandomMultiplication() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		// when
		Multiplication multiplication = multiplicationService.createRandomMultiplication();

		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		assertThat(multiplication.getResult()).isEqualTo(1500);
	}
	*/
}
