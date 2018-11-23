package com.mytaxi.android_demo.screens;

import android.content.Intent;
import android.net.Uri;

import com.mytaxi.android_demo.R;

import javax.inject.Inject;
import javax.inject.Singleton;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@Singleton
public class DriverProfileScreen {

    protected static final int DRIVER_NAME_FIELD_ID = R.id.textViewDriverName;
    protected static final int DIAL_BUTTON_ID = R.id.fab;

    @Inject
    DriverProfileScreen() {
    }

    /**
     * Checks if the {@link com.mytaxi.android_demo.activities.DriverProfileActivity} is displayed.
     * It looks for the driver name field on the screen.
     */
    public DriverProfileScreen checkProfileIsDisplayedForDriver(String driverName) {
        onView(withId(DRIVER_NAME_FIELD_ID))
                .check(matches(withText(driverName)));
        return this;
    }

    public DriverProfileScreen clickOnDialButton() {
        onView(withId(DIAL_BUTTON_ID))
                .perform(click());
        return this;
    }

    public DriverProfileScreen checkDialedNumber(String phoneNumber) {
        intended(allOf(
                hasAction(Intent.ACTION_DIAL),
                hasData(Uri.parse("tel:" + phoneNumber))));
        return this;
    }
}
