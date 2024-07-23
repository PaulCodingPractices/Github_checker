package com.example.github_checker.ExceptionHandling;

public class GithubUserNotFoundException extends RuntimeException {

    public GithubUserNotFoundException(String message) {
        super(message);
    }
}