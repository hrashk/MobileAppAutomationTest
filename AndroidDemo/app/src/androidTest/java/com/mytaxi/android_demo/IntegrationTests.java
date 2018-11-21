package com.mytaxi.android_demo;

import android.content.Context;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.dependencies.component.DaggerTestComponent;
import com.mytaxi.android_demo.dependencies.component.TestComponent;
import com.mytaxi.android_demo.dependencies.module.CannedResponseModule;
import com.mytaxi.android_demo.models.User;
import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.NavigationDrawerScreen;
import com.mytaxi.android_demo.utils.CannedDispatcher;
import com.mytaxi.android_demo.utils.storage.Storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;

import javax.inject.Inject;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
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

    @Inject
    AuthenticationScreen mAuthenticationScreen;
    @Inject
    MainScreen mMainScreen;
    @Inject
    DriverProfileScreen mDriverProfileScreen;
    @Inject
    NavigationDrawerScreen mNavigationDrawerScreen;

    @Inject
    IdlingResource mResource;
    @Inject
    Storage mStorage;

    @Rule
    public final MockWebServer mServer = new MockWebServer();

    /**
     * The activity is not launched right away so that we have a chance to set things up
     */
    @Rule
    public final IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

    protected void injectDependencies() {
        TestComponent component = DaggerTestComponent.builder()
                .cannedResponseModule(new CannedResponseModule(mServer.url("/app")))
                .build();

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        App app = App.getApplicationContext(context);
        app.setComponent(component);

        component.inject(this);
    }

    /**
     * Set up fixtures then launch the {@link MainActivity}
     * Inject dependencies. Register the idling resources related to OkHttpClient.
     * Configure the storage mock to emulate the situation when there is no user logged in when
     * the app is launched for the first time, but then return a valid user after successful
     * authentication.
     * In general, this approach enables testing of various negative scenarios such as preferences
     * store becoming corrupt and not finding the user after a call to {@link Storage#saveUser(User)}
     */
    @Before
    public void setThingsUp() throws IOException {
        injectDependencies();

        mServer.setDispatcher(new CannedDispatcher());

        Mockito.when(mStorage.loadUser()).thenReturn(null, LOGGEDIN_USER);

        IdlingRegistry.getInstance().register(mResource);


        mActivityRule.launchActivity(null);  // launch the main activity
    }

    @After
    public void unregisterIdlingResources() throws IOException {
        if (mResource != null)
            IdlingRegistry.getInstance().unregister(mResource);
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
