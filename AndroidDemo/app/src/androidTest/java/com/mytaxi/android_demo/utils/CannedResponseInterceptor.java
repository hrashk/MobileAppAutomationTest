package com.mytaxi.android_demo.utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.mytaxi.android_demo.misc.Constants.RANDOM_USER_URL;

/**
 * A simple interceptor is used to return canned JSON results. For more sophisticated testing, use
 * https://github.com/square/okhttp/tree/master/mockwebserver
 */
@Deprecated
class CannedResponseInterceptor implements Interceptor {
    final String USER_DATA;
    final String DRIVERS_DATA;

    public CannedResponseInterceptor() throws IOException {
        USER_DATA = AssetReader.readTestAsset("user.json");
        DRIVERS_DATA = AssetReader.readTestAsset("drivers.json");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String responseString;

        final HttpUrl url = chain.request().url();
        final String query = url.query();

        if (!url.toString().contains(RANDOM_USER_URL) || query == null || !query.contains("seed="))
            return chain.proceed(chain.request());

        if (query.contains("results=")) {
            responseString = DRIVERS_DATA;
        } else {
            responseString = USER_DATA;
        }

        return new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }
}
