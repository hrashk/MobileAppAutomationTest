package com.mytaxi.android_demo.utils;

import android.content.Context;

import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import androidx.test.espresso.IdlingRegistry;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class Helpers {

    public static void registerIdlingResources() {
        IdlingRegistry.getInstance().register(HttpClient.getIdlingResource());
    }

    public static void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(HttpClient.getIdlingResource());
    }
}
