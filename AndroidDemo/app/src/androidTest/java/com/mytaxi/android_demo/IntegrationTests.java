package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.NavigationDrawerScreen;
import com.mytaxi.android_demo.screens.ScreenFactory;
import com.mytaxi.android_demo.utils.Helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static com.mytaxi.android_demo.data.AuthenticationData.PASSWORD;
import static com.mytaxi.android_demo.data.AuthenticationData.USERNAME;
import static com.mytaxi.android_demo.data.AuthenticationData.resetLoggedInUser;
import static com.mytaxi.android_demo.data.DriverData.DEFAULT_DRIVER_NAME;
import static com.mytaxi.android_demo.data.DriverData.DEFAULT_PHONE_NUMBER;
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
    protected NavigationDrawerScreen mNavigationDrawerScreen;

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

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
        mNavigationDrawerScreen = ScreenFactory.getNavigationDrawerScreen();
    }

    @Test
    public void checkLoginAndLogout() {
        // When the user authenticates herself
        mAuthenticationScreen.authenticateUser(USERNAME, PASSWORD);
        mMainScreen.checkIsDisplayed(); // Then the main screen appears

        mNavigationDrawerScreen.logoutUser(USERNAME); // When the user logs out
        mAuthenticationScreen.checkIsDisplayed();   // Then the authentication screen appears
    }

    @Test
    public void checkSearchingDefaultDriver() {
        // When the user authenticates herself
        mAuthenticationScreen.authenticateUser(USERNAME, PASSWORD);

        mMainScreen.checkIsDisplayed()  // Then the main screen appears
                .searchForDrivers(SEARCH_STRING) // When the user searches for sa
                // Then only drivers with that prefix are shown
                .checkSearchResultsStartWith(SEARCH_STRING)
                .selectDriverByName(DEFAULT_DRIVER_NAME); // When selecting the 2nd driver
        // Then the driver profile screen is displayed
        mDriverProfileScreen.checkIsDisplayedForDriver(DEFAULT_DRIVER_NAME)
                .clickOnDialButton() // When the user clicks on the dial button
                // Then the driver's phone number is passed to the dialer app
                .checkDialedNumber(DEFAULT_PHONE_NUMBER);
    }
}
