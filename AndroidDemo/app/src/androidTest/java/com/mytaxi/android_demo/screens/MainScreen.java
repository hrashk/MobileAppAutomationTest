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
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.mytaxi.android_demo.utils.StringStartsWithIgnoringCaseMatcher.startsWithIgnoringCase;
import static com.mytaxi.android_demo.utils.WithDriverNameMatcher.withDriverName;
import static com.mytaxi.android_demo.utils.WithEveryItemMatcher.withEveryItem;
import static org.hamcrest.Matchers.is;

public class MainScreen {

    protected static final int SEARCH_FIELD_ID = R.id.textSearch;
    private static final String POPUP_CLASSNAME = "android.widget.DropDownListView";

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

    /**
     * The way this is implemented is probably a hack as it relies on the hardcoded class name
     * of the drop-down view.
     * TODO: find a better way of doing this.
     *
     * @param prefix
     * @return
     */
    public MainScreen checkSearchResultsStartWith(String prefix) {
        onView(withClassName(is(POPUP_CLASSNAME)))
                .inRoot(isPlatformPopup())
                .check(matches(withEveryItem(withDriverName(startsWithIgnoringCase(prefix)))));
        return this;
    }
}
