package tech.ericntd.githubsearch;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;
import tech.ericntd.githubsearch.repositories.RealGitHubRepositoryImpl;

public class GitHubRepoProvider {
    private static class SingletonHelper {
        static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        private static final GitHubRepository INSTANCE = new RealGitHubRepositoryImpl(retrofit
                .create(GitHubApi.class));
    }

    public static GitHubRepository provide() {
        return SingletonHelper.INSTANCE;
    }
}
