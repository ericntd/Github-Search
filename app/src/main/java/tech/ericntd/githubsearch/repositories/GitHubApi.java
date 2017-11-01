package tech.ericntd.githubsearch.repositories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import tech.ericntd.githubsearch.models.SearchResponse;

public interface GitHubApi {
    /**
     * Search with topics supported https://developer.github.com/v3/search/
     * @param term search keywords e.g. "testing topic:android"
     * @return
     */
    @Headers({"Accept: application/vnd.github.mercy-preview+json"})
    @GET("search/repositories")
    Call<SearchResponse> searchRepos(@Query("q") String term);
}
