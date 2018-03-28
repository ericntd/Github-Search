package tech.ericntd.githubsearch;

import tech.ericntd.githubsearch.repositories.FakeGithubRepoImpl;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

public class GitHubRepoProvider {
    private static class SingletonHelper {
        private static final GitHubRepository INSTANCE = new FakeGithubRepoImpl();
    }

    public static GitHubRepository provide() {
        return SingletonHelper.INSTANCE;
    }
}
