package com.example.github_checker;

import com.example.github_checker.ExceptionHandling.GithubUserNotFoundException;
import com.example.github_checker.model.RepositoryInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GithubController.class)
public class GithubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @Test
    public void testGetRepositories() throws Exception {
        RepositoryInfo repo1 = new RepositoryInfo();
        repo1.setName("repo1");
        RepositoryInfo.Owner owner = new RepositoryInfo.Owner();
        owner.setLogin("owner1");
        repo1.setOwner(owner);
        repo1.setFork(false);

        RepositoryInfo repo2 = new RepositoryInfo();
        repo2.setName("repo2");
        RepositoryInfo.Owner owner2 = new RepositoryInfo.Owner();
        owner2.setLogin("owner2");
        repo2.setOwner(owner2);
        repo2.setFork(false);

        List<RepositoryInfo> repos = Arrays.asList(repo1, repo2);

        when(githubService.getRepositories("validUser")).thenReturn(repos);

        mockMvc.perform(MockMvcRequestBuilders.get("/repos")
                .param("username", "validUser")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].owner.login").value("owner1"))
                .andExpect(jsonPath("$[1].name").value("repo2"))
                .andExpect(jsonPath("$[1].owner.login").value("owner2"));
    }

    @Test
    public void testGetRepositoriesForNonExistentUser() throws Exception {
        when(githubService.getRepositories("nonExistentUser")).thenThrow(new GithubUserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/repos")
                .param("username", "nonExistentUser")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testGetRepositoriesWithoutAcceptHeader() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/repos")
                .param("username", "validUser"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRepositoriesWithoutUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/repos")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRepositoriesWithEmptyUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/repos")
                .param("username", "")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

}

