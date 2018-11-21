package com.mytaxi.android_demo.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import androidx.test.platform.app.InstrumentationRegistry;
import okio.Okio;

public final class AssetReader {
    public static String readTestAsset(String path) throws IOException {
        Context ctx = InstrumentationRegistry.getInstrumentation().getContext();
        InputStream is = ctx.getResources().getAssets().open(path);
        return Okio.buffer(Okio.source(is)).readUtf8();
    }
}
