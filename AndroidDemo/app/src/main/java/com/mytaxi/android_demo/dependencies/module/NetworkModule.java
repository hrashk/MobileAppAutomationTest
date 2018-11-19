package com.mytaxi.android_demo.dependencies.module;

import com.google.gson.JsonParser;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static com.mytaxi.android_demo.misc.Constants.SOCKET_TIMEOUT;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return getBuilder().build();
    }

    protected OkHttpClient.Builder getBuilder() {
        return new OkHttpClient.Builder().readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);
    }

    @Singleton
    @Provides
    JsonParser provideJsonParser() {
        return new JsonParser();
    }

}
