package com.mytaxi.android_demo.utils;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class CannedDispatcher extends Dispatcher {
    final String USER_DATA;
    final String DRIVERS_DATA;

    public CannedDispatcher() throws IOException {
        USER_DATA = AssetReader.readTestAsset("user.json");
        DRIVERS_DATA = AssetReader.readTestAsset("drivers.json");
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        String body = request.getRequestUrl().queryParameter("results") != null
                ? DRIVERS_DATA : USER_DATA;
        return new MockResponse().setResponseCode(200).setBody(body);
    }
}
