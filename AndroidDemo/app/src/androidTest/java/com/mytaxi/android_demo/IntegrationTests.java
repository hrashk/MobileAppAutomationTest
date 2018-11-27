package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.utils.CannedDispatcher;
import com.mytaxi.android_demo.utils.DependencyInjector;
import com.mytaxi.android_demo.utils.OkHttpIdlingResourceRule;
import com.mytaxi.android_demo.utils.storage.Storage;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import okhttp3.mockwebserver.MockWebServer;

import static com.mytaxi.android_demo.data.AuthenticationData.LOGGEDIN_USER;
import static com.mytaxi.android_demo.data.AuthenticationData.PASSWORD;
import static com.mytaxi.android_demo.data.AuthenticationData.USERNAME;
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

    @ClassRule
    public static final MockWebServer mServer = new MockWebServer();

    private DependencyInjector mInjector = new DependencyInjector(mServer.url("/app"));

    @Rule
    public final OkHttpIdlingResourceRule mIdlingResource =
            new OkHttpIdlingResourceRule(mInjector.getComponent().httpClient());

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public final IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

    /**
     * Set up fixtures then launch the {@link MainActivity}
     * Configure the storage mock to emulate the situation when there is no user logged in when
     * the app is launched for the first time, but then return a valid user after successful
     * authentication.
     * In general, this approach enables testing of various negative scenarios such as preferences
     * store becoming corrupt and not finding the user after a call to {@link Storage#saveUser(User)}
     */
    @Before
    public void setThingsUp() throws IOException {
        mServer.setDispatcher(new CannedDispatcher());

        Mockito.when(mInjector.getComponent().storage().loadUser())
                .thenReturn(null, LOGGEDIN_USER);

        mActivityRule.launchActivity(null);  // launch the main activity
    }

    @Test
    public void checkLoginAndLogout() {
        // When the user authenticates herself
        mInjector.getComponent().authentication()
                .authenticateValidUser(USERNAME, PASSWORD)
                .checkMainScreenIsDisplayed(); // Then the main screen appears

        mInjector.getComponent().navigationDrawer()
                .logoutUser(USERNAME) // When the user logs out
                .checkAuthScreenIsDisplayed();
    }

    @Test
    public void checkSearchingDefaultDriver() {
        // When the user authenticates herself
        mInjector.getComponent().authentication()
                .authenticateValidUser(USERNAME, PASSWORD)
                .checkMainScreenIsDisplayed()  // Then the main screen appears
                .searchForDrivers(SEARCH_STRING) // When the user searches for sa
                // Then only drivers with that prefix are shown
                .checkSearchResultsStartWith(SEARCH_STRING)
                .selectDriverByName(DEFAULT_DRIVER_NAME) // When selecting the 2nd driver

                .checkProfileIsDisplayedForDriver(DEFAULT_DRIVER_NAME)
                .clickOnDialButton() // When the user clicks on the dial button
                // Then the driver's phone number is passed to the dialer app
                .checkDialedNumber(DEFAULT_PHONE_NUMBER);
    }
}
