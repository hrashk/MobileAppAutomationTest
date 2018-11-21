package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.utils.CannedDispatcher;
import com.mytaxi.android_demo.utils.storage.Storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
public class IntegrationTests extends BaseTests {

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

        Mockito.when(mStorage.loadUser()).thenReturn(null, LOGGEDIN_USER);

        mActivityRule.launchActivity(null);  // launch the main activity
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
