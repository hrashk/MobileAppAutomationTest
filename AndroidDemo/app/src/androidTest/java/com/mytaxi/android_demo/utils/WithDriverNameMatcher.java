package com.mytaxi.android_demo.utils;

import com.mytaxi.android_demo.models.Driver;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.is;

public class WithDriverNameMatcher extends TypeSafeMatcher<Driver> {
    private final Matcher<String> stringMatcher;

    private WithDriverNameMatcher(Matcher<String> stringMatcher) {
        this.stringMatcher = stringMatcher;
    }

    public static Matcher<Driver> withDriverName(Matcher<String> stringMatcher) {
        return new WithDriverNameMatcher(stringMatcher);
    }

    public static Matcher<Driver> withDriverName(String driverName) {
        return withDriverName(is(driverName));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with driver name: ");
        stringMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(Driver driver) {
        return stringMatcher.matches(driver.getName());
    }
}
