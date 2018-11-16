package com.mytaxi.android_demo.utils;

import android.content.Context;

import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import androidx.test.espresso.IdlingRegistry;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class Helpers {
    /**
     * Clear the logged in user info from shared preferences
     * In order to have a more fine grained control, inject a mock prefs storage into the activity
     * that would allow controlling whether a user is logged in in each test function
     * but that would also make the test less black-box.
     */
    public static void resetLoggedInUser() {
        Context targetContext = getInstrumentation().getTargetContext();
        SharedPrefStorage sps = new SharedPrefStorage(targetContext);
        sps.resetUser();
    }

    public static void registerIdlingResources() {
        IdlingRegistry.getInstance().register(HttpClient.getIdlingResource());
    }

    public static void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(HttpClient.getIdlingResource());
    }
}
