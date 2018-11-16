package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.ScreenFactory;
import com.mytaxi.android_demo.utils.Helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.mytaxi.android_demo.data.AuthenticationData.PASSWORD;
import static com.mytaxi.android_demo.data.AuthenticationData.USERNAME;
import static com.mytaxi.android_demo.data.AuthenticationData.resetLoggedInUser;
import static com.mytaxi.android_demo.data.DriverData.DEFAULT_DRIVER_NAME;
import static com.mytaxi.android_demo.data.DriverData.SEARCH_STRING;
import static com.mytaxi.android_demo.utils.Helpers.registerIdlingResources;

/**
 * There are two integration tests here
 *
 * @see #checkLoginAndLogout()
 * @see #checkSearchingDefaultDriver()
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTests {

    protected AuthenticationScreen mAuthenticationScreen;
    protected MainScreen mMainScreen;
    protected DriverProfileScreen mDriverProfileScreen;

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void launchActivity() {
        resetLoggedInUser();

        registerIdlingResources();

        // launch the main activity
        mActivityRule.launchActivity(null);
    }

    @After
    public void unregisterIdlingResources() {
        Helpers.unregisterIdlingResources();
    }

    @Before
    public void getScreens() {
        mAuthenticationScreen = ScreenFactory.getAuthenticationScreen();
        mMainScreen = ScreenFactory.getmMainScreen();
        mDriverProfileScreen = ScreenFactory.getDriverProfileScreen();
    }

    @Test
    public void checkLoginAndLogout() {
        // When the user authenticates herself
        mAuthenticationScreen.authenticateUser(USERNAME, PASSWORD);

        mMainScreen.checkIsDisplayed(); // Then the main screen appears

        // when the user logs out
        // then the authentication screen appears
    }

    @Test
    public void checkSearchingDefaultDriver() {
        // When user credentials are entered
        onView(withId(R.id.edt_username))
                .perform(replaceText(USERNAME));
        onView(withId(R.id.edt_password))
                .perform(replaceText(PASSWORD));
        // And the user clicks on log in
        onView(withId(R.id.btn_login))
                .perform(click());


        mMainScreen.checkIsDisplayed()  // Then the main screen appears
                .searchForDrivers(SEARCH_STRING) // When I search for sa
                .selectDriverByName(DEFAULT_DRIVER_NAME); // And select the default driver

        // Then the driver profile screen is displayed
        mDriverProfileScreen.checkIsDisplayed()
                .clickOnDialButton(); // When the starts dialing the driver

        // then the system dialer is launched
    }
}
