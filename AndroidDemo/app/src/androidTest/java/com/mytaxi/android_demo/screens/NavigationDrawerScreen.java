package com.mytaxi.android_demo.screens;

import com.mytaxi.android_demo.R;

import androidx.test.espresso.contrib.DrawerActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class NavigationDrawerScreen {

    protected static final int DRAWER_LAYOUT_ID = R.id.drawer_layout;
    protected static final int USERNAME_LABEL_ID = R.id.nav_username;
    protected static final int NAVIGATION_VIEW_ID = R.id.nav_view;
    protected static final int LOGOUT_BUTTON_ID = R.id.nav_logout;

    protected NavigationDrawerScreen() {
    }

    public NavigationDrawerScreen openNavigationDrawer() {
        onView(withId(DRAWER_LAYOUT_ID))
                .perform(DrawerActions.open());
        return this;
    }

    public NavigationDrawerScreen checkShowsUsername(String username) {
        onView(withId(USERNAME_LABEL_ID))
                .check(matches(withText(username)));
        return this;
    }

    public NavigationDrawerScreen clickOnLogoutButton() {
        onView(withId(NAVIGATION_VIEW_ID))
                .perform(navigateTo(LOGOUT_BUTTON_ID));
        return this;
    }

    public NavigationDrawerScreen logoutUser(String username) {
        return openNavigationDrawer()
                .checkShowsUsername(username)
                .clickOnLogoutButton();
    }
}
