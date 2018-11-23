package com.mytaxi.android_demo.screens;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Screens {

    @Inject
    public AuthenticationScreen authentication;
    @Inject
    public MainScreen main;
    @Inject
    public DriverProfileScreen driverProfile;
    @Inject
    public NavigationDrawerScreen navigationDrawer;

    @Inject
    Screens() {
    }
}
