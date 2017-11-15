package tech.ericntd.githubsearch.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.repositories.GitHubRepository;
import tech.ericntd.githubsearch.utils.StringUtils;

/**
 * ======= PRESENTATION LAYER
 * <p>
 * Simplified version of MVP-Clean as described
 * <a href="https://github.com/android10/Android-CleanArchitecture">here</a> and
 * <a href="https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/">here</a>
 * (Merged Domain Layer into the Presenter)
 * --------
 */
public class SearchPresenter implements SearchPresenterContract, GitHubRepository.GithubRepositoryCallback {

    private final SearchViewContract viewContract;
    private final GitHubRepository repository;

    SearchPresenter(@NonNull SearchViewContract viewContract,
                    GitHubRepository repository) {
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
    public void searchGitHubRepos(@Nullable String query) {
        if (!StringUtils.isEmpty(query)) {
            repository.searchRepos(query, this);
        }
    }

    public void handleGitHubResponse(@NonNull Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null && searchResponse.getSearchResults() != null) {
                viewContract.displaySearchResults(searchResponse.getSearchResults());
            } else {
                Log.w("", "empty response");
                viewContract.displayError();
            }
        } else {
            Log.w("", "not a success");
            viewContract.displayError();
        }
    }

    public void handleGitHubError() {
        viewContract.displayError();
    }
}
