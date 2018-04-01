package tech.ericntd.githubsearch.search;

import dagger.Module;
import dagger.Provides;
import tech.ericntd.githubsearch.repositories.GitHubRepository;

@Module
public class MainActivityModule {

    @Provides
    SearchViewContract provideMainView(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    SearchPresenter provideMainPresenter(SearchViewContract view,
                                         GitHubRepository gitHubRepository) {
        return new SearchPresenter(view, gitHubRepository);
    }
}