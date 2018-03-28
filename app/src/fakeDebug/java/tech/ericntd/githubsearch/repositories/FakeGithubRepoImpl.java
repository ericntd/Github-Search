package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;

import tech.ericntd.githubsearch.repositories.GitHubRepository;


public class FakeGithubRepoImpl implements GitHubRepository {
    @Override
    public void searchRepos(@NonNull String query,
                            @NonNull GitHubRepositoryCallback callback) {

    }
}
