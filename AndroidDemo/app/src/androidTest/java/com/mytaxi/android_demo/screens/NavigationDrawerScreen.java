package com.mytaxi.android_demo.screens;

import com.mytaxi.android_demo.R;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class NavigationDrawerScreen {

    protected NavigationDrawerScreen() {
    }

    public NavigationDrawerScreen openNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        return this;
    }

    public NavigationDrawerScreen checkShowsUsername(String username) {
        onView(withId(R.id.nav_username)).check(matches(withText(username)));
        return this;
    }

    public NavigationDrawerScreen clickOnLogoutButton() {
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));
        return this;
    }

    public NavigationDrawerScreen logoutUser(String username) {
        return openNavigationDrawer()
                .checkShowsUsername(username)
                .clickOnLogoutButton();
    }
}
