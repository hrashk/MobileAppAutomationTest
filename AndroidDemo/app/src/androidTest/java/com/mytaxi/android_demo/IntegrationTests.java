package com.mytaxi.android_demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasProperty;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTests {

    // todo: move this to a test data helper class
    // the class will have to be extended to extract the password too
    private static final String USERNAME = "crazydog335";
    private static final String PASSWORD = "venture";

    private IdlingResource mIdlingResource;

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void launchActivity() {
        // reset the login info so that we can run several login tests
        // todo: to have a more fine grained control, inject a mock prefs storage into the activity
        // todo: that would allow controlling whether a user is logged in each test function
        // todo: but that would also make the test less black-box
        Context targetContext = getInstrumentation().getTargetContext();
        SharedPrefStorage sps = new SharedPrefStorage(targetContext);
        sps.resetUser();

        // register the idling resource. TODO: move this into a util method/helper
        mIdlingResource = HttpClient.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);

        // launch the main activity
        mActivityRule.launchActivity(null);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void checkLoginAndLogout() {
        // using replaceText instead of typeText to avoid the issue in landscape mode
        // When user credentials are entered
        onView(withId(R.id.edt_username))
                .perform(replaceText(USERNAME));
        onView(withId(R.id.edt_password))
                .perform(replaceText(PASSWORD));
        // And the user clics on log in
        onView(withId(R.id.btn_login))
                .perform(click());

        // Then the search text box appears
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));
    }

    @Test
    public void performSearch() {
        // When user credentials are entered
        onView(withId(R.id.edt_username))
                .perform(replaceText(USERNAME));
        onView(withId(R.id.edt_password))
                .perform(replaceText(PASSWORD));
        // And the user clicks on log in
        onView(withId(R.id.btn_login))
                .perform(click());

        // Then the search text box appears
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

        // when I search for sa
        onView(withId(R.id.textSearch)).perform(typeText("sa"), closeSoftKeyboard());
        // and click on Sarah Scott
//        onData(hasProperty("name", equalTo("Sarah Scott")))
        onView(withText("Sarah Scott"))
                .inRoot(isPlatformPopup())
                .perform(click());
        // then the driver profile is displayed
        onView(withId(R.id.textViewDriverName)).check(matches(isDisplayed()));

        // when I click on dial
        onView(withId(R.id.fab)).perform(click());

        // then the system dialer is called
        // todo: implement the intent filter here
    }
}
