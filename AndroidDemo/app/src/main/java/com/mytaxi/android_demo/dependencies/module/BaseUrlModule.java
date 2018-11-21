package com.mytaxi.android_demo.dependencies.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;

import static com.mytaxi.android_demo.misc.Constants.RANDOM_USER_URL;

@Module
public class BaseUrlModule {

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse(RANDOM_USER_URL);
    }

}
