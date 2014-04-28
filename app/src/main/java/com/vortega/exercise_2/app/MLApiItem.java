package com.vortega.exercise_2.app;

import android.app.Activity;
import android.os.AsyncTask;
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

/**
 * Created by vortega on 27/04/14.
 */
public class MLApiItem extends AsyncTask<String, Void, ItemDto> {

    Activity activity;
    AsyncTaskListener callback;

    public MLApiItem(Activity act) {
        this.activity = act;
        this.callback = (AsyncTaskListener) act;
    }

    public ItemDto doInBackground(String... itemsIds) {
        String itemId = itemsIds[0];
        HttpClient client = new DefaultHttpClient();
        String searchUrl = "https://api.mercadolibre.com/items/";
        try {
            searchUrl += URLEncoder.encode(itemId, "UTF-8");
        } catch (Exception e) {
            Log.e("AsyncTask", "Problema Armando URL", e);
        }

        HttpGet request = new HttpGet(searchUrl);

        // Get the response
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        ItemDto item = null;

        try {
            String resp = client.execute(request, responseHandler);
            JSONObject json = new JSONObject(resp);

            JSONObject firstPic = new JSONObject();

            if (json.getJSONArray("pictures").length() > 0 ) {
                firstPic = json.getJSONArray("pictures").getJSONObject(0);
            }

            item = new ItemDto(
                json.getString("id"),
                json.getString("title"),
                new BigDecimal( String.valueOf(json.getDouble("price")) ),
                json.getString("thumbnail"),
                firstPic.optString("url","")
            );
        } catch (Exception e) {
            Log.e("AsyncTask", "Problema Search", e);
        }

        return item;
    }

    protected void onPostExecute(ItemDto item) {
        super.onPostExecute(item);
        //callback.getRequestDone(item);
    }
}
