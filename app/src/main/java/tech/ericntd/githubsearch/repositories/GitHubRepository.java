package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;

public interface GitHubRepository {
    void searchRepos(@NonNull String query,
                     @NonNull GitHubRepositoryCallback callback);

    public interface GitHubRepositoryCallback {
        void handleGitHubResponse(Response<SearchResponse> response);

        void handleGitHubError();
    }
}
