package com.vortega.exercise_2.app;

import android.app.Service;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MLService extends Service {

    private static final String TAG = MLService.class.getSimpleName();
    private final IBinder mlBinder = new MLBinder();

    public class MLBinder extends Binder {
        MLService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MLService.this;
        }
    }

    public MLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mlBinder;
    }

    public List<ItemDto> getSearch(String itemStr) {
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
        List<ItemDto> items = new ArrayList<ItemDto>();

        try {
            String resp = client.execute(request, responseHandler);
            JSONObject json = new JSONObject(resp);
            JSONArray results = json.getJSONArray("results");

            for( int i = 0; i < results.length(); i++ ) {
                JSONObject itemResult = results.getJSONObject(i);

                ItemDto item = new ItemDto(
                        itemResult.getString("id"),
                        itemResult.getString("title"),
                        new BigDecimal( String.valueOf(itemResult.getDouble("price")) ),
                        itemResult.getString("thumbnail")
                );
                items.add( item );
            }


        } catch (Exception e) {
            Log.e(TAG, "Problema Search", e);
        }

        return items;
    }
}
  
