package com.mytaxi.android_demo.dependencies.component;

import com.mytaxi.android_demo.IntegrationTests;
import com.mytaxi.android_demo.dependencies.module.IdlingResourceModule;
import com.mytaxi.android_demo.dependencies.module.NetworkModule;
import com.mytaxi.android_demo.dependencies.module.PermissionModule;
import com.mytaxi.android_demo.dependencies.module.SharedPrefStorageModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, PermissionModule.class,
        SharedPrefStorageModule.class, IdlingResourceModule.class})
public interface TestComponent extends AppComponent {
    void inject(IntegrationTests integrationTests);
}
