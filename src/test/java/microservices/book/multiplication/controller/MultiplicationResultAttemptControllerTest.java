package microservices.book.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author weitao
 * 2020-01-11 下午 19:23
 */

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	private MockMvc mvc;

	private JacksonTester<MultiplicationResultAttempt> jsonResult;
	//practical v3
	//private JacksonTester<ResultResponse> jsonResponse;
	private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

	@Before
	public void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParemeterizedTest(true);
	}

	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParemeterizedTest(false);
	}

	private void genericParemeterizedTest(final boolean correct) throws Exception {
		// given (remember we're not testing here the service itself)
		given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(correct);
		User user = new User("john");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/results").contentType(MediaType.APPLICATION_JSON)
						.content(jsonResult.write(attempt).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonResult.write(
						//practical v3
						//new ResultResponse(correct)
						//MultiplicationResultAttempt 包含 correct，返回了一个更大的Json
						new MultiplicationResultAttempt(
								attempt.getUser(),
								attempt.getMultiplication(),
								attempt.getResultAttempt(),
								correct
						)
				).getJson());
	}

	@Test
	public void getUserStats() throws Exception {
		// given
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		ArrayList<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("john_doe"))
				.willReturn(recentAttempts);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/results").param("alias", "john_doe"))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonResultAttemptList.write(
						recentAttempts
				).getJson());
	}
}
