package tech.ericntd.githubsearch;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApp extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends MyApp> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
