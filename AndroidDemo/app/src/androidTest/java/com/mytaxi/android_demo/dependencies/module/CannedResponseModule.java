package com.mytaxi.android_demo.dependencies.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

@Module
public class CannedResponseModule extends NetworkModule {
    private HttpUrl mUrl;

    public CannedResponseModule(HttpUrl url){
        mUrl = url;
    }

    @Singleton
    @Provides
    HttpUrl provideMockedBaseUrl() {
        return mUrl;
    }
}
