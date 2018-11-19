package com.mytaxi.android_demo.dependencies.module;

import dagger.Module;
import okhttp3.OkHttpClient;

@Module
public class CannedResponseModule extends NetworkModule {

    @Override
    protected OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder builder = super.getBuilder();
        builder.addInterceptor(new CannedResponseInterceptor());
        return builder;
    }
}
