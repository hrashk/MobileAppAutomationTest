package com.mytaxi.android_demo.screens;

import com.mytaxi.android_demo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class DriverProfileScreen {

    protected static final int DRIVER_NAME_FIELD_ID = R.id.textViewDriverName;
    protected static final int DIAL_BUTTON_ID = R.id.fab;

    protected DriverProfileScreen() {
    }

    /**
     * Checks if the {@link com.mytaxi.android_demo.activities.DriverProfileActivity} is displayed.
     * It looks for the driver name field on the screen.
     */
    public DriverProfileScreen checkIsDisplayed() {
        onView(withId(DRIVER_NAME_FIELD_ID))
                .check(matches(isDisplayed()));
        return this;
    }

    public DriverProfileScreen clickOnDialButton() {
        onView(withId(DIAL_BUTTON_ID))
                .perform(click());
        return this;
    }
}
