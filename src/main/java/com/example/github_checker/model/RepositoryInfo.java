package com.example.github_checker.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RepositoryInfo {

    private String name;
    private Owner owner;
    private boolean fork;
    private List<BranchInfo> branches; // Add this line

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("fork")
    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public List<BranchInfo> getBranches() { // Add this method
        return branches;
    }

    public void setBranches(List<BranchInfo> branches) { // Add this method
        this.branches = branches;
    }

    public static class Owner {
        private String login;

        @JsonProperty("login")
        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }
}