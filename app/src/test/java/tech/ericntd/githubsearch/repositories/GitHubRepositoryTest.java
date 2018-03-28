package tech.ericntd.githubsearch.repositories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;

public class GitHubRepositoryTest {
    private GitHubRepository repository;

    @Mock
    private GitHubApi gitHubApi;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        repository = Mockito.spy(new RealGitHubRepositoryImpl(gitHubApi));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchRepos() {
        /*
        You can also do <code>GitHubRepository.GithubRepositoryCallback githubRepositoryCallback
        = Mockito.mock(GitHubRepository.GithubRepositoryCallback.class)</code> here but mocking
        is slower than normal code execution.
        An actual production app would easily contain a few thousands to tens of thousands unit
        tests so the difference will be significant
         */
        RealGitHubRepositoryImpl.GitHubRepositoryCallback githubRepositoryCallback = new RealGitHubRepositoryImpl
                .GitHubRepositoryCallback() {
            @Override
            public void handleGitHubResponse(Response<SearchResponse> response) {

            }

            @Override
            public void handleGitHubError() {

            }
        };
        Call call = Mockito.mock(Call.class);
        String searchQuery = "some query";
        Mockito.doReturn(call).when(gitHubApi).searchRepos(searchQuery);
        Mockito.doNothing().when(call).enqueue(Mockito.any(Callback.class));

        // trigger
        repository.searchRepos(searchQuery, githubRepositoryCallback);
        // validation
        Mockito.verify(gitHubApi, Mockito.times(1)).searchRepos(searchQuery);
    }
}
