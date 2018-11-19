package com.mytaxi.android_demo.dependencies.module;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import javax.inject.Singleton;

import androidx.test.espresso.IdlingResource;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class IdlingResourceModule {

    /**
     * Building an idling resource for the HTTP client using Jake Wharton's library
     * https://github.com/JakeWharton/okhttp-idling-resource
     *
     * @param client - the OkHttp client that this resource wraps
     * @return - the idling resource for the supplied client
     */
    @Singleton
    @Provides
    IdlingResource providesHttpIdlingResource(OkHttpClient client) {
        return OkHttp3IdlingResource.create("OkHttp", client);
    }

}
