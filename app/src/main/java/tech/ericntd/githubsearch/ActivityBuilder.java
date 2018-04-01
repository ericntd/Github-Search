package tech.ericntd.githubsearch;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.ericntd.githubsearch.search.MainActivity;
import tech.ericntd.githubsearch.search.MainActivityModule;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();
}