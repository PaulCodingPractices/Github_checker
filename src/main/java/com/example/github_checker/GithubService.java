package com.example.github_checker;

import com.example.github_checker.ExceptionHandling.GithubUserNotFoundException;
import com.example.github_checker.model.RepositoryInfo;
import com.example.github_checker.model.BranchInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RepositoryInfo> getRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        RepositoryInfo[] repositories;
        try {
            repositories = restTemplate.getForObject(url, RepositoryInfo[].class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new GithubUserNotFoundException("User not found");
        }

        return Arrays.stream(repositories)
                     .filter(repo -> !repo.isFork())
                     .peek(repo -> repo.setBranches(getBranches(username, repo.getName())))
                     .collect(Collectors.toList());
    }

    private List<BranchInfo> getBranches(String username, String repoName) {
        String url = "https://api.github.com/repos/" + username + "/" + repoName + "/branches";
        BranchInfo[] branches = restTemplate.getForObject(url, BranchInfo[].class);
        return Arrays.asList(branches);
    }
}