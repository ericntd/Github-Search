package com.eric.core;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {
        NetworkModule.class})
public interface CoreComponent {
    Retrofit getRetrofit();
}
