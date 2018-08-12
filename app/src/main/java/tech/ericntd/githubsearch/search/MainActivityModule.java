package tech.ericntd.githubsearch.search;

import com.eric.core.ActivityScope;

import dagger.Module;
import dagger.Provides;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

@Module
public class MainActivityModule {

    @ActivityScope
    @Provides
    SearchViewContract provideMainView(MainActivity mainActivity) {
        return mainActivity;
    }

    @ActivityScope
    @Provides
    SearchPresenter provideMainPresenter(SearchViewContract view,
                                         GitHubRepository gitHubRepository) {
        return new SearchPresenter(view, gitHubRepository);
    }
}