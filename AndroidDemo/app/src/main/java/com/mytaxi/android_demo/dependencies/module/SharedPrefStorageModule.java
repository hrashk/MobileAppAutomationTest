package com.mytaxi.android_demo.dependencies.module;

import android.content.Context;

import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;
import com.mytaxi.android_demo.utils.storage.Storage;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefStorageModule {

    private Context mContext;

    public SharedPrefStorageModule(Context context) {
        mContext = context;
    }

    /**
     * Decoupling implmenetation from the interface.
     *
     * @return the concrete shared storage as implemented in the app
     */
    @Provides
    Storage provideSharedPrefStorage() {
        return new SharedPrefStorage(mContext);
    }

}
