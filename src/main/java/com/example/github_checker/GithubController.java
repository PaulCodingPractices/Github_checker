package com.example.github_checker;

import com.example.github_checker.model.RepositoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/repos")
    public List<RepositoryInfo> getRepositories(@RequestHeader(value = "Accept", required = false) String acceptHeader,
                                                @RequestParam(value = "username", required = false) String username) {
        if (acceptHeader == null || !acceptHeader.equals("application/json")) {
            throw new IllegalArgumentException("Invalid Accept header");
        }

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        return githubService.getRepositories(username);
    }
}
