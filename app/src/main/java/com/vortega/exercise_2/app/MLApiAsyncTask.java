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
public class MLApiAsyncTask extends AsyncTask<String, Void, List<ItemDto>> {

    Activity activity;
    AsyncTaskListener callback;

    public MLApiAsyncTask(Activity act) {
        this.activity = act;
        this.callback = (AsyncTaskListener) act;
    }

    public List<ItemDto> doInBackground(String... itemStrs) {
        String itemStr = itemStrs[0];
        HttpClient client = new DefaultHttpClient();
        String searchUrl = "https://api.mercadolibre.com/sites/MLA/search?limit=100&offset=0";
        try {
            searchUrl += "&q=" + URLEncoder.encode(itemStr, "UTF-8");
        } catch (Exception e) {
            Log.e("AsyncTask", "Problema Armando URL", e);
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
            Log.e("AsyncTask", "Problema Search", e);
        }

        return items;
    }

    protected void onPostExecute(List<ItemDto> items) {
        super.onPostExecute(items);
        callback.searchDone(items);
    }
}
