package com.example.apidemo_mvp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestHelper {

    static String getStringFromJsonFile(Context context, String filepath) {
        StringBuilder stringBuilder = new StringBuilder();
        try(InputStream inputStream = context.getResources().getAssets().open(filepath)) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
