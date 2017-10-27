package tech.ericntd.githubsearch;

import android.support.annotation.NonNull;
import android.util.Log;

import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.models.SearchResponse;

public class SearchPresenter implements SearchPresenterContract {

    private final SearchViewContract viewContract;

    public SearchPresenter(SearchViewContract viewContract) {
        this.viewContract = viewContract;
    }

    /**
     * @param query search query e.g. "android view stars:>1000 topic:android"
     */
    @Override
    public void searchGitHubRepos(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubSearchService service = retrofit.create(GitHubSearchService.class);
        Call<SearchResponse> call = service.searchRepos(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call,
                                   Response<SearchResponse> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<SearchResponse> call,
                                  Throwable t) {
                Log.e("", "onFailure", t);
            }
        });
    }

    @DebugLog
    private void handleResponse(@NonNull Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null) {
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
}
