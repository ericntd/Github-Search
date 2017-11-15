package tech.ericntd.githubsearch.search;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(repository).setCallback(Mockito.any(GitHubRepository.Callback
                .class));

        presenter = Mockito.spy(new SearchPresenter(viewContract, repository));
    }

    @Test
    public void searchGitHubRepos_noQuery() {
        String searchQuery = null;

        // Trigger
        presenter.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.never()).searchRepos(searchQuery);
    }

    @Test
    public void searchGitHubRepos() {
        String searchQuery = "some query";

        // Trigger
        presenter.searchGitHubRepos(searchQuery);

        // Validation
        Mockito.verify(repository, Mockito.times(1)).searchRepos(searchQuery);
    }

    /**
     * By default, <code>Response response = Mockito.mock(Response.class)</code> will not work
     * since Retrofit's Response is final class
     * you will see an error "Mockito cannot mock/spy because : - final class"
     * <p>
     * That problem is overcome for Mockito 2+ following the instruction <a href="https://github
     * .com/mockito/mockito/wiki/What's-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of
     * -final-classesmethods">here</a>
     */
    @Test
    public void handleGitHubResponse_Success() {
        Response response = Mockito.mock(Response.class);
        SearchResponse searchResponse = Mockito.mock(SearchResponse.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(searchResponse).when(response).body();
        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.add(new SearchResult());
        searchResults.add(new SearchResult());
        searchResults.add(new SearchResult());
        Mockito.doReturn(searchResults).when(searchResponse).getSearchResults();

        presenter.handleGitHubResponse(response);

        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResults(searchResults);
    }

    @Test
    public void handleGitHubError() {
        presenter.handleGitHubError();

        Mockito.verify(presenter, Mockito.times(1)).handleGitHubError();
    }
}
