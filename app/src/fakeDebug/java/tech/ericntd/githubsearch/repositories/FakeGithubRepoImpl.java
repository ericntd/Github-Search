package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;
import tech.ericntd.githubsearch.models.SearchResult;


public class FakeGithubRepoImpl implements GitHubRepository {
    @Override
    public void searchRepos(@NonNull String query,
                            @NonNull GitHubRepositoryCallback callback) {
        List<SearchResult> resultList = new ArrayList<>();
        resultList.add(new SearchResult("repo 1"));
        resultList.add(new SearchResult("repo 2"));
        resultList.add(new SearchResult("repo 3"));
        SearchResponse searchResponse = new SearchResponse(resultList.size(), resultList);
        Response<SearchResponse> response = Response.success(searchResponse);
        callback.handleGitHubResponse(response);
    }
}
