package tech.ericntd.githubsearch;

import com.eric.core.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.ericntd.githubsearch.search.MainActivity;
import tech.ericntd.githubsearch.search.MainActivityModule;

@Module
public abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();
}