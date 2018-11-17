package com.mytaxi.android_demo.utils;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringStartsWithIgnoringCaseMatcher extends SubstringMatcher {
    public StringStartsWithIgnoringCaseMatcher(String substring) {
        super(substring);
    }

    @Override
    protected boolean evalSubstringOf(String s) {
        return s.toLowerCase().startsWith(substring.toLowerCase());
    }

    @Override
    protected String relationship() {
        return "starting with ignoring case";
    }

    @Factory
    public static Matcher<String> startsWithIgnoringCase(String prefix) {
        return new StringStartsWithIgnoringCaseMatcher(prefix);
    }
}
