package microservices.book.multiplication.controller;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author weitao
 * 2020-01-11 下午 19:19
 */
@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

	private final MultiplicationService multiplicationService;

	@Autowired
	public MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}

	@PostMapping
	ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {

		boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
		MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(multiplicationResultAttempt.getUser(),
				multiplicationResultAttempt.getMultiplication(),
				multiplicationResultAttempt.getResultAttempt(),
				isCorrect);
		return ResponseEntity.ok(attemptCopy);
	}

	@GetMapping
	ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
		return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
	}

	/*
	// practical v3
	@PostMapping
	ResponseEntity<ResultResponse> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
		return ResponseEntity.ok(
				new ResultResponse(multiplicationService
						.checkAttempt(multiplicationResultAttempt)));
	}*/

	/**
	// practical v3
	@RequiredArgsConstructor
	@NoArgsConstructor(force = true)
	@Getter
	static final class ResultResponse {
		private final boolean correct;
	}
	*/
}
