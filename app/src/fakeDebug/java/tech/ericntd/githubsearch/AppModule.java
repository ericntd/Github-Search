package tech.ericntd.githubsearch;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.repositories.FakeGithubRepoImpl;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;
import tech.ericntd.githubsearch.repositories.RealGitHubRepositoryImpl;

@Module
public class AppModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    GitHubRepository provideGitHubRepo(Retrofit retrofit) {
        return new FakeGithubRepoImpl();
    }
}
