package tech.ericntd.githubsearch;

import com.eric.core.CoreComponent;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApp> {
    }
}