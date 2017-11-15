package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;

/**
 * ======= DATA LAYER
 * <p>
 * Simplified version of MVP-Clean as described
 * <a href="https://github.com/android10/Android-CleanArchitecture">here</a> and
 * <a href="https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/">here</a>
 * (Merged Domain Layer into the Presenter)
 * -------
 * <p>
 * "Repository" in the class name here means "repository" layer in MVP-Clean architecture, don't
 * get confused with the repository objects we are retrieving here
 */
public class GitHubRepository {
    private final GitHubApi remoteApi;
    private Callback callback;

    public GitHubRepository(GitHubApi remoteApi) {
        this.remoteApi = remoteApi;
    }

    public void searchRepos(@NonNull String query) {
        Call<SearchResponse> call = remoteApi.searchRepos(query);
        call.enqueue(new retrofit2.Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call,
                                   @NonNull Response<SearchResponse> response) {
                callback.handleGitHubResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call,
                                  @NonNull Throwable t) {
                callback.handleGitHubError();
            }
        });
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void handleGitHubResponse(Response<SearchResponse> response);

        void handleGitHubError();
    }
}
