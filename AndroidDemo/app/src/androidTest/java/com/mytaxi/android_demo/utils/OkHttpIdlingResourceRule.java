package com.mytaxi.android_demo.utils;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.ExternalResource;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import okhttp3.OkHttpClient;

@Singleton
public class OkHttpIdlingResourceRule extends ExternalResource {

    private IdlingResource mResource;

    @Inject
    public OkHttpIdlingResourceRule(OkHttpClient client) {
        mResource = OkHttp3IdlingResource.create("OkHttp", client);
    }

    @Override
    protected void before() throws Throwable {
        IdlingRegistry.getInstance().register(mResource);
    }

    @Override
    protected void after() {
        IdlingRegistry.getInstance().unregister(mResource);
    }
}
