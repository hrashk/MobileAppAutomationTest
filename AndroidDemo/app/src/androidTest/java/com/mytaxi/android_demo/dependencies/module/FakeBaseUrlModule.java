package com.mytaxi.android_demo.dependencies.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;

@Module
public class FakeBaseUrlModule {
    private HttpUrl mUrl;

    public FakeBaseUrlModule(HttpUrl url){
        mUrl = url;
    }

    @Singleton
    @Provides
    HttpUrl provideFakeBaseUrl() {
        return mUrl;
    }
}
