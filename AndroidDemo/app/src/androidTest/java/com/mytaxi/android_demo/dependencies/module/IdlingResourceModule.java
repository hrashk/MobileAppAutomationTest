package com.mytaxi.android_demo.dependencies.module;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import javax.inject.Singleton;

import androidx.test.espresso.IdlingResource;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class IdlingResourceModule {

    @Singleton
    @Provides
    IdlingResource providesHttpIdlingResource(OkHttpClient client) {
        return OkHttp3IdlingResource.create("OkHttp", client);
    }

}
