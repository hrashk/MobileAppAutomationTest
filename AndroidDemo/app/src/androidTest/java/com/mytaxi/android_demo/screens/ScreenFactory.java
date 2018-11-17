package com.mytaxi.android_demo.screens;

/**
 * A factory for screens.
 * TODO: see if it can be replaced with Dagger 2 singletons & injection into the test classes.
 */
public class ScreenFactory {
    protected static AuthenticationScreen mAuthenticationScreen = new AuthenticationScreen();
    protected static MainScreen mMainScreen = new MainScreen();
    protected static DriverProfileScreen mDriverProfileScreen = new DriverProfileScreen();
    protected static NavigationDrawerScreen mNavigationDrawerScreen = new NavigationDrawerScreen();

    public static AuthenticationScreen getAuthenticationScreen() {
        return mAuthenticationScreen;
    }

    public static MainScreen getmMainScreen() {
        return mMainScreen;
    }

    public static DriverProfileScreen getDriverProfileScreen() {
        return mDriverProfileScreen;
    }

    public static NavigationDrawerScreen getNavigationDrawerScreen() {
        return mNavigationDrawerScreen;
    }
}
