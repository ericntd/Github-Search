package tech.ericntd.githubsearch.search;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class SearchPresenterTest {
    private SearchPresenter presenter;

    @Mock
    private GitHubRepository repository;
    @Mock
    private SearchViewContract viewContract;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations

        // Make presenter a mock while using mock repository and viewContract created above
        presenter = Mockito.spy(new SearchPresenter(viewContract, repository));
    }

    @Test
    public void searchGitHubRepos_noQuery() {
        String searchQuery = null;

        // Trigger
        presenter.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.never()).searchRepos(searchQuery, presenter);
    }

    @Test
    public void searchGitHubRepos() {
        String searchQuery = "some query";

        // Trigger
        presenter.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.times(1)).searchRepos(searchQuery, presenter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handleGitHubResponse_Success() {
        Response response = Mockito.mock(Response.class);
        SearchResponse searchResponse = Mockito.mock(SearchResponse.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(searchResponse).when(response).body();
        List<SearchResult> searchResults = Collections.singletonList(new SearchResult());
        Mockito.doReturn(searchResults).when(searchResponse).getSearchResults();

        // Trigger
        presenter.handleGitHubResponse(response);

        // Validation
        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResults(searchResults);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handleGitHubResponse_Failure() {
        Response response = Mockito.mock(Response.class);
        Mockito.doReturn(false).when(response).isSuccessful();

        // Trigger
        presenter.handleGitHubResponse(response);

        // Validation
        Mockito.verify(viewContract, Mockito.times(1)).displayError("E101 - System error");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void handleGitHubResponse_EmptyResponse() {
        Response response = Mockito.mock(Response.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(null).when(response).body();

        // Trigger
        presenter.handleGitHubResponse(response);

        // Validation
        Mockito.verify(viewContract, Mockito.times(1)).displayError("E102 - System error");
    }

    @Test
    public void handleGitHubError() {
        // Trigger
        presenter.handleGitHubError();

        // Validation
        Mockito.verify(viewContract, Mockito.times(1)).displayError();
    }
}
