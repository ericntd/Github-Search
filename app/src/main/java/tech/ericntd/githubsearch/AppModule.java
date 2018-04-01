package tech.ericntd.githubsearch;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.ericntd.githubsearch.repositories.GitHubApi;
import tech.ericntd.githubsearch.repositories.GitHubRepository;
import tech.ericntd.githubsearch.repositories.RealGitHubRepositoryImpl;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

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
        return new RealGitHubRepositoryImpl(retrofit.create(GitHubApi.class));
    }
}
