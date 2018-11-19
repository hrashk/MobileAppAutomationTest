package com.mytaxi.android_demo.dependencies.module;

import com.google.gson.JsonParser;
import com.mytaxi.android_demo.utils.network.HttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.test.espresso.idling.CountingIdlingResource;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static com.mytaxi.android_demo.misc.Constants.SOCKET_TIMEOUT;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS).build();
    }

    @Singleton
    @Provides
    JsonParser provideJsonParser() {
        return new JsonParser();
    }

}
