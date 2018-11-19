package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.NavigationDrawerScreen;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ScreenModule {

    @Singleton
    @Provides
    MainScreen provideMainScreen() {
        return new MainScreen();
    }

    @Singleton
    @Provides
    AuthenticationScreen provideAuthenticationScreen() {
        return new AuthenticationScreen();
    }

    @Singleton
    @Provides
    DriverProfileScreen provideMDriverProfileScreen() {
        return new DriverProfileScreen();
    }

    @Singleton
    @Provides
    NavigationDrawerScreen provideNavigationDrawerScreen() {
        return new NavigationDrawerScreen();
    }

}
