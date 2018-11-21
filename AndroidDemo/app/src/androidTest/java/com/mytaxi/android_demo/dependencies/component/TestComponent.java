package com.mytaxi.android_demo.dependencies.component;

import com.mytaxi.android_demo.IntegrationTests;
import com.mytaxi.android_demo.dependencies.module.FakeBaseUrlModule;
import com.mytaxi.android_demo.dependencies.module.MockPrefStorageModule;
import com.mytaxi.android_demo.dependencies.module.NetworkModule;
import com.mytaxi.android_demo.dependencies.module.PermissionModule;
import com.mytaxi.android_demo.dependencies.module.ScreenModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FakeBaseUrlModule.class, MockPrefStorageModule.class, ScreenModule.class,
        PermissionModule.class, NetworkModule.class})
public interface TestComponent extends AppComponent {
    void inject(IntegrationTests integrationTests);
}
