package com.mytaxi.android_demo.data;

import android.content.Context;

import com.mytaxi.android_demo.IntegrationTests;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AuthenticationData {

    public static final String USERNAME = "crazydog335";
    public static final String PASSWORD = "venture";
    public static final String INVALID_PASSWORD = "random";
    public static final User LOGGEDIN_USER = new User(USERNAME, "random", "random");

    /**
     * Clear the logged in user info from shared preferences to be able to run several tests
     * in sequence that start from the authentication screen. It uses the app's functionality.
     * This is now deprecated in favor of Mockito stubs
     * @see IntegrationTests#setThingsUp()
     */
    @Deprecated
    public static void resetLoggedInUser() {
        Context targetContext = getInstrumentation().getTargetContext();
        SharedPrefStorage sps = new SharedPrefStorage(targetContext);
        sps.resetUser();
    }
}
