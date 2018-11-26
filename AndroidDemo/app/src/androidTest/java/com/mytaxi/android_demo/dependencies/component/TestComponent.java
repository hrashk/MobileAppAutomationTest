package com.mytaxi.android_demo.dependencies.component;

import com.mytaxi.android_demo.BaseTests;
import com.mytaxi.android_demo.dependencies.module.FakeBaseUrlModule;
import com.mytaxi.android_demo.dependencies.module.MockPrefStorageModule;
import com.mytaxi.android_demo.dependencies.module.NetworkModule;
import com.mytaxi.android_demo.dependencies.module.PermissionModule;
import com.mytaxi.android_demo.screens.AuthenticationScreen;
import com.mytaxi.android_demo.screens.DriverProfileScreen;
import com.mytaxi.android_demo.screens.MainScreen;
import com.mytaxi.android_demo.screens.NavigationDrawerScreen;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FakeBaseUrlModule.class, MockPrefStorageModule.class,
        PermissionModule.class, NetworkModule.class})
public interface TestComponent extends AppComponent {
    void inject(BaseTests integrationTests);

    AuthenticationScreen authentication();

    MainScreen main();

    DriverProfileScreen driverProfile();

    NavigationDrawerScreen navigationDrawer();
}
