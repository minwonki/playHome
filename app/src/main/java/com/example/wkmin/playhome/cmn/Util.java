package com.example.wkmin.playhome.cmn;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String readJSONFromAssets(Context context) {

        String json = null;
        try {
            InputStream is = context.getAssets().open("result.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }
}
