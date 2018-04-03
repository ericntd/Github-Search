package tech.ericntd.githubsearch.search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class SearchViewModelTest {
    private SearchViewModel viewModel;

    @Mock
    private GitHubRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations

        // Make viewModel a mock while using mock repository and viewContract created above
        viewModel = Mockito.spy(new SearchViewModel(repository));
    }

    @Test
    public void searchGitHubRepos_noQuery() {
        String searchQuery = null;

        // Trigger
        viewModel.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.never()).searchRepos(searchQuery, viewModel);
    }

    @Test
    public void searchGitHubRepos() {
        String searchQuery = "some query";

        // Trigger
        viewModel.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.times(1)).searchRepos(searchQuery, viewModel);
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
        viewModel.handleGitHubResponse(response);

        // Validation
        Mockito.verify(viewModel, Mockito.times(1)).renderSuccess(searchResponse);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void renderSuccess() {
        Response response = Mockito.mock(Response.class);
        SearchResponse searchResponse = Mockito.mock(SearchResponse.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(searchResponse).when(response).body();
        Mockito.doReturn(1001).when(searchResponse).getTotalCount();

        // Trigger
        viewModel.handleGitHubResponse(response);

        // Validation
        Assert.assertEquals("Number of results: 1001", viewModel.status.get());
    }
}
