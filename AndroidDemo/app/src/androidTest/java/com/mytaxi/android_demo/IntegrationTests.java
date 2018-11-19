package com.mytaxi.android_demo;

import android.app.Instrumentation;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.dependencies.component.TestComponent;
import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.NavigationDrawerScreen;
import com.mytaxi.android_demo.screens.ScreenFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import static com.mytaxi.android_demo.data.AuthenticationData.PASSWORD;
import static com.mytaxi.android_demo.data.AuthenticationData.USERNAME;
import static com.mytaxi.android_demo.data.AuthenticationData.resetLoggedInUser;
import static com.mytaxi.android_demo.data.DriverData.DEFAULT_DRIVER_NAME;
import static com.mytaxi.android_demo.data.DriverData.DEFAULT_PHONE_NUMBER;
import static com.mytaxi.android_demo.data.DriverData.SEARCH_STRING;

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

    @Inject
    IdlingResource mResource;

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

    protected void injectDependencies() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TestApp app = (TestApp) instrumentation.getTargetContext().getApplicationContext();
        TestComponent component = (TestComponent) app.getAppComponent();
        component.inject(this);
    }

    @Before
    public void launchActivity() {
        injectDependencies();

        resetLoggedInUser();

        IdlingRegistry.getInstance().register(mResource);

        // launch the main activity
        mActivityRule.launchActivity(null);
    }

    @After
    public void unregisterIdlingResources() {
        if (mResource != null)
            IdlingRegistry.getInstance().unregister(mResource);
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
