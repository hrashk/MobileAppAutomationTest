package com.mytaxi.android_demo.utils;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import androidx.test.espresso.matcher.BoundedMatcher;

public class WithEveryItemMatcher extends BoundedMatcher<View, AdapterView> {
    private final Matcher<?> itemMatcher;

    private WithEveryItemMatcher(Matcher<?> driverMatcher) {
        super(AdapterView.class);
        this.itemMatcher = driverMatcher;
    }

    public static Matcher<View> withEveryItem(Matcher<?> itemMatcher) {
        return new WithEveryItemMatcher(itemMatcher);
    }

    @Override
    protected boolean matchesSafely(AdapterView view) {
        Adapter adapter = view.getAdapter();
        int itemsCount = adapter.getCount();
        for (int i = 0; i < itemsCount; i++) {
            if (!itemMatcher.matches(adapter.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Checks that all items in the AdapterView match the given criteria");
    }
}
