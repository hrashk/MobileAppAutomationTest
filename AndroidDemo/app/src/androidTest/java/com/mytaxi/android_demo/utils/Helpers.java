package com.mytaxi.android_demo.utils;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import okhttp3.OkHttpClient;

public class Helpers {
    static IdlingResource mResource;

    public static void registerIdlingResources(OkHttpClient mClient) {
        mResource = OkHttp3IdlingResource.create("OkHttp", mClient);
        IdlingRegistry.getInstance().register(mResource);
    }

    public static void unregisterIdlingResources() {
        if (mResource != null)
            IdlingRegistry.getInstance().unregister(mResource);
    }
}
