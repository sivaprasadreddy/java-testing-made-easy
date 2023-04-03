package com.sivalabs.tcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GithubService {

    private final String githubApiBaseUrl;

    public GithubService(@Value("${github.api.base-url}") String githubApiBaseUrl) {
        this.githubApiBaseUrl = githubApiBaseUrl;
    }

    public GitHubUser getGithubUserProfile(String username) {
        try {
            log.info("Github API BaseUrl:" + githubApiBaseUrl);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(githubApiBaseUrl + "/users/" + username, GitHubUser.class);
        } catch (RuntimeException e) {
            log.error("Fail to fetch github profile", e);
            throw new GitHubServiceException("Fail to fetch github profile for " + username);
        }
    }

}
