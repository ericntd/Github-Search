package tech.ericntd.githubsearch.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.repositories.GitHubRepository;
import tech.ericntd.githubsearch.repositories.RealGitHubRepositoryImpl;

public class SearchPresenter implements SearchPresenterContract, RealGitHubRepositoryImpl
        .GitHubRepositoryCallback {

    private final SearchViewContract viewContract;
    private final GitHubRepository repository;

    SearchPresenter(@NonNull final SearchViewContract viewContract,
                    @NonNull final GitHubRepository repository) {
        this.viewContract = viewContract;
        this.repository = repository;
    }

    /**
     * In a MVP architecutre, the responsibility to fetch data from remote or local sources
     * should be delegated to the "repositories" in the Data Layer
     * <p>
     * The presenter should focus on the business logics e.g. filter, sort, combine the data to
     * present in the view
     *
     * @param query search query e.g. "android view stars:>1000 topic:android"
     */
    @Override
    public void searchGitHubRepos(@Nullable final String query) {
        if (query != null && query.length() > 0) {
            repository.searchRepos(query, this);
        }
    }

    @Override
    public void handleGitHubResponse(@NonNull final Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null && searchResponse.getSearchResults() != null) {
                viewContract.displaySearchResults(searchResponse.getSearchResults(), searchResponse.getTotalCount());
            } else {
                viewContract.displayError("E102 - System error");
            }
        } else {
            viewContract.displayError("E101 - System error");
        }
    }

    @Override
    public void handleGitHubError() {
        viewContract.displayError();
    }
}
