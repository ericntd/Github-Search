package tech.ericntd.githubsearch.search;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.Locale;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class SearchViewModel implements GitHubRepository
        .GitHubRepositoryCallback {

    public ObservableField<String> status = new ObservableField<>();

    private final GitHubRepository repository;

    SearchViewModel(@NonNull final GitHubRepository repository) {
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
                renderSuccess(searchResponse);
            }
        }
    }

    @VisibleForTesting
    void renderSuccess(SearchResponse searchResponse) {
        status.set(String.format(Locale.US, "Number of results: %d", searchResponse
                .getTotalCount()));
    }

    @Override
    public void handleGitHubError() {
        Log.w("", "handleGitHubError");
    }
}
