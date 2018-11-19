package com.mytaxi.android_demo.screens;

import com.mytaxi.android_demo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Using replaceText instead of typeText to avoid an issue in landscape mode with popup edit boxes.
 * TODO: research a proper Espresso way for doing this.
 */
public class AuthenticationScreen {

    protected static final int USERNAME_FIELD_ID = R.id.edt_username;
    protected static final int PASSWORD_FIELD_ID = R.id.edt_password;
    protected static final int LOGIN_BUTTON_ID = R.id.btn_login;

    /**
     * Enter username & password, then click on the Login button.
     *
     * @param username to enter
     * @param password to enter
     */
    public AuthenticationScreen authenticateUser(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickOnLoginButton();
    }

    public AuthenticationScreen enterUsername(String username) {
        onView(withId(USERNAME_FIELD_ID))
                .perform(replaceText(username));
        return this;
    }

    public AuthenticationScreen enterPassword(String password) {
        onView(withId(PASSWORD_FIELD_ID))
                .perform(replaceText(password));
        return this;
    }

    public AuthenticationScreen clickOnLoginButton() {
        onView(withId(LOGIN_BUTTON_ID))
                .perform(click());
        return this;
    }

    public AuthenticationScreen checkIsDisplayed() {
        onView(withId(USERNAME_FIELD_ID))
                .check(matches(isDisplayed()));
        return this;
    }
}
