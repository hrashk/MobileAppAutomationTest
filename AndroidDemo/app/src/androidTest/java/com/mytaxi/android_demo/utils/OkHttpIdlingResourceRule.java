package com.mytaxi.android_demo.utils;

import org.junit.rules.ExternalResource;

import javax.inject.Inject;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;

public class OkHttpIdlingResourceRule extends ExternalResource {

    @Inject
    IdlingResource mResource;

    @Override
    protected void before() throws Throwable {
        IdlingRegistry.getInstance().register(mResource);
    }

    @Override
    protected void after() {
        IdlingRegistry.getInstance().unregister(mResource);
    }
}
