package com.vortega.exercise_2.app;

import android.app.Service;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.net.URLEncoder;

public class MLService extends Service {

    private static final String TAG = MLService.class.getSimpleName();

    public MLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        this.onCreate();
        Log.i(TAG, "Created");
    }

    public JSONObject getSearch(String itemStr) {
        HttpClient client = new DefaultHttpClient();
        String searchUrl = "https://api.mercadolibre.com/sites/MLA/search?limit=100&offset=0";
        try {
            searchUrl += "&q=" + URLEncoder.encode(itemStr, "UTF-8");
        } catch (Exception e) {
            Log.e(TAG, "Problema Armando URL", e);
        }

        HttpGet request = new HttpGet(searchUrl);

        // Get the response
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        JSONObject json;
        try {
            String resp = client.execute(request, responseHandler);
            json = new JSONObject(resp);
        } catch (Exception e) {
            Log.e(TAG, "Problema Search", e);
        }

        return json;
    }
}
