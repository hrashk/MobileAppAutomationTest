package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.utils.storage.Storage;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockPrefStorageModule {

    @Singleton
    @Provides
    Storage provideMockStorage() {
        return Mockito.mock(Storage.class);
    }
}
