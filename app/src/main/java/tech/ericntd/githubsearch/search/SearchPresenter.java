package tech.ericntd.githubsearch.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

/**
 * ======= PRESENTATION LAYER
 * <p>
 * Simplified version of MVP-Clean as described
 * <a href="https://github.com/android10/Android-CleanArchitecture">here</a> and
 * <a href="https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/">here</a>
 * (Merged Domain Layer into the Presenter)
 * --------
 */
public class SearchPresenter implements SearchPresenterContract, GitHubRepository
        .GitHubRepositoryCallback {

    private final SearchViewContract viewContract;
    private final GitHubRepository repository;

    SearchPresenter(@NonNull final SearchViewContract viewContract,
                    @NonNull final GitHubRepository repository) {
        this.viewContract = viewContract;
        this.repository = repository;
    }

    /**
     * In a MVP-Clean architecutre, the presenter should not handle the communication with the
     * remote API.
     * Instead, communication with data sources should be done on the "repository" layer,
     * see {@link GitHubRepository}.
     * The presenter should focus on the business logics e.g. filter, sort, combine the GitHub
     * repos to returned to the view.
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
                viewContract.displayError("empty response");
            }
        } else {
            viewContract.displayError("failed to get GitHub repos");
        }
    }

    @Override
    public void handleGitHubError() {
        viewContract.displayError();
    }
}
