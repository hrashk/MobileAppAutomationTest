package com.mytaxi.android_demo.data;

import android.content.Context;

import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AuthenticationData {

    public static final String USERNAME = "crazydog335";
    public static final String PASSWORD = "venture";
    public static final String INVALID_PASSWORD = "random";

    /**
     * Clear the logged in user info from shared preferences to be able to run several tests
     * in sequence that start from the authentication screen.
     * In order to have a more fine grained control, inject a mock prefs storage into the activity
     * that would allow controlling whether a user is logged in in each test function,
     * but that would also make the test less black-box.
     */
    public static void resetLoggedInUser() {
        Context targetContext = getInstrumentation().getTargetContext();
        SharedPrefStorage sps = new SharedPrefStorage(targetContext);
        sps.resetUser();
    }
}
