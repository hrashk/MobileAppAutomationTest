package com.mytaxi.android_demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntegrationTests {

    // todo: fetch username & password from the URL using the network.HttpClient class
    // the class will have to be extended to extract the password too
    private static final String USERNAME = "crazydog335";
    private static final String PASSWORD = "venture";

    /**
     * Use {@link ActivityScenario} to create and launch the activity under test. This is a
     * replacement for {@link androidx.test.rule.ActivityTestRule}.
     */
    @Before
    public void launchActivity() {
//        System.err.println("Launching activity: " + MainActivity.class.getCanonicalName());
        // reset the login info so that we can run several login tests
        // todo: the shared prefs storage should probably be injected with Dagger
        Context targetContext = getInstrumentation().getTargetContext();
        SharedPrefStorage sps = new SharedPrefStorage(targetContext);
        sps.resetUser();
//        SharedPreferences.Editor preferencesEditor =
//                PreferenceManager.getDefaultSharedPreferences(targetContext).edit();
//        preferencesEditor.remove();
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void checkLogin() {
        // When user credwntials are entered
        onView(withId(R.id.edt_username))
                .perform(typeText(USERNAME));
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());
        // And the user clics on log in
        onView(withId(R.id.btn_login))
                .perform(click());

        // Then the search text box appears
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    @Test
    public void performSearch() {
        // login activity should launch by default
        // Type text and then press the button.
        onView(withId(R.id.edt_username))
                .perform(typeText(USERNAME));
        onView(withId(R.id.edt_password))
                .perform(typeText(PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.btn_login))
                .perform(click());

        // Check that the search text is displayed, i.e. login was successful
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }
}
