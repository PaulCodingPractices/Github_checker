package com.example.github_checker;

import com.example.github_checker.model.RepositoryInfo;
import com.example.github_checker.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {

    @Autowired
    private com.example.github_checker.GithubService githubService;

    @GetMapping("/repos")
    public List<RepositoryInfo> getRepositories(@RequestHeader("Accept") String acceptHeader,
                                                @RequestParam String username) {
        return githubService.getRepositories(username);
    }
}
