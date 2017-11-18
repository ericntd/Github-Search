package tech.ericntd.githubsearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import tech.ericntd.githubsearch.models.SearchResponse;

public interface GitHubApi {
    @Headers({"Accept: application/vnd.github.mercy-preview+json"})
    @GET("search/repositories")
    Call<SearchResponse> searchRepos(@Query("q") String term);
}
