package com.mytaxi.android_demo.screens;

import com.mytaxi.android_demo.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.mytaxi.android_demo.utils.WithDriverNameMatcher.withDriverName;

public class MainScreen {

    protected static final int SEARCH_FIELD_ID = R.id.textSearch;

    protected MainScreen() {
    }

    /**
     * Checks if the {@link com.mytaxi.android_demo.activities.MainActivity} is displayed.
     * It looks for the search field on the screen.
     */
    public MainScreen checkIsDisplayed() {
        onView(withId(SEARCH_FIELD_ID))
                .check(matches(isDisplayed()));
        return this;
    }

    public MainScreen searchForDrivers(String searchString) {
        onView(withId(SEARCH_FIELD_ID))
                .perform(typeText(searchString), closeSoftKeyboard());
        return this;
    }

    public MainScreen selectDriverByName(String name) {
        onData(withDriverName(name))
                .inRoot(isPlatformPopup())
                .perform(click());
        return this;
    }
}
