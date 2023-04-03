package com.sivalabs.tcdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GithubController {

	private final GithubService githubService;

	@GetMapping("/users/{username}")
	public ResponseEntity<GitHubUser> getGithubUserProfile(@PathVariable String username) {
		GitHubUser githubUserProfile = githubService.getGithubUserProfile(username);
		return ResponseEntity.ok(githubUserProfile);
	}

	@ExceptionHandler(GitHubServiceException.class)
	ResponseEntity<ApiError> handle(GitHubServiceException e){
		ApiError apiError = new ApiError(e.getMessage());
		return ResponseEntity.internalServerError().body(apiError);
	}

}