package com.mytaxi.android_demo;

import com.mytaxi.android_demo.dependencies.component.AppComponent;
import com.mytaxi.android_demo.dependencies.component.DaggerTestComponent;

public class TestApp extends App {
    @Override
    protected AppComponent createComponent() {
        return DaggerTestComponent.builder().build();
    }
}
