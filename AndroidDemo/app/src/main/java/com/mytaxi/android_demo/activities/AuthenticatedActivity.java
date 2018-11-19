package com.mytaxi.android_demo.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mytaxi.android_demo.App;
import com.mytaxi.android_demo.dependencies.component.AppComponent;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;
import com.mytaxi.android_demo.utils.storage.Storage;

import javax.inject.Inject;

public class AuthenticatedActivity extends AppCompatActivity {

    @Inject
    Storage mSharedPrefStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = App.getApplicationContext(this).getAppComponent();
        appComponent.inject(this);
    }

    protected boolean isAuthenticated() {
        return mSharedPrefStorage.loadUser() != null;
    }

}
