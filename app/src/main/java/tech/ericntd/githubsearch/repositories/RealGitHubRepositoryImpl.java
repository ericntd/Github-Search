package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

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
 * <p>
 * TODO unit test this using <a href="https://stackoverflow.com/a/35826483/541624">these
 * instructions</>
 */
public class RealGitHubRepositoryImpl implements GitHubRepository {
    private final GitHubApi gitHubApi;

    public RealGitHubRepositoryImpl(@NonNull final GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void searchRepos(@NonNull final String query,
                            @NonNull final GitHubRepositoryCallback callback) {
        final CountingIdlingResource countingIdlingResource = new CountingIdlingResource
                ("githubrepo");
        IdlingRegistry.getInstance().register(countingIdlingResource);
        countingIdlingResource.increment();
        final Call<SearchResponse> call = gitHubApi.searchRepos(query);
        call.enqueue(new retrofit2.Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call,
                                   @NonNull Response<SearchResponse> response) {
                countingIdlingResource.decrement();
                callback.handleGitHubResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call,
                                  @NonNull Throwable t) {
                Log.e("", "onFailure", t);
                countingIdlingResource.decrement();
                callback.handleGitHubError();
            }
        });
    }

}
