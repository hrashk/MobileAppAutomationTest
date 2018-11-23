package com.mytaxi.android_demo.screens;

import android.widget.AdapterView;

import com.mytaxi.android_demo.R;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.mytaxi.android_demo.matchers.StringStartsWithIgnoringCaseMatcher.startsWithIgnoringCase;
import static com.mytaxi.android_demo.matchers.WithDriverNameMatcher.withDriverName;
import static com.mytaxi.android_demo.matchers.WithEveryItemMatcher.withEveryItem;

public class MainScreen {

    protected static final int SEARCH_FIELD_ID = R.id.textSearch;

    @Inject
    DriverProfileScreen mDriverProfileScreen;

    @Inject
    MainScreen() {
    }

    /**
     * Checks if the {@link com.mytaxi.android_demo.activities.MainActivity} is displayed.
     * It looks for the search field on the screen.
     */
    public MainScreen checkMainScreenIsDisplayed() {
        onView(withId(SEARCH_FIELD_ID))
                .check(matches(isDisplayed()));
        return this;
    }

    public MainScreen searchForDrivers(String searchString) {
        onView(withId(SEARCH_FIELD_ID))
                .perform(typeText(searchString), closeSoftKeyboard());
        return this;
    }

    public DriverProfileScreen selectDriverByName(String name) {
        onData(withDriverName(name))
                .inRoot(isPlatformPopup())
                .perform(click());
        return mDriverProfileScreen;
    }

    public MainScreen checkSearchResultsStartWith(String prefix) {
        onView(isAssignableFrom(AdapterView.class))
                .inRoot(isPlatformPopup())
                .check(matches(withEveryItem(withDriverName(startsWithIgnoringCase(prefix)))));
        return this;
    }
}
