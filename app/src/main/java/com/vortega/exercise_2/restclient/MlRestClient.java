package com.vortega.exercise_2.restclient;

import android.app.Activity;
import android.util.Log;

import com.vortega.exercise_2.app.ItemDto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vortega on 03/05/14.
 */
public class MlRestClient implements AsyncTaskListener {
    MlApiCallbacks callbacks;
    MLApiAsyncTask task;

    public MlRestClient(Activity act) {
        new MLApiAsyncTask(this);
        this.callbacks = (MlApiCallbacks) act;
    }

    public void processResponse(String resp){
        if ( resp.contains("results") ) {
            buildItemsList(resp);
        } else {
            buildItem(resp);
        }
    }

    /*** SEARCH ***/

    public void search(String searchStr) {
        String searchUrl = "https://api.mercadolibre.com/sites/MLA/search?limit=100&offset=0";
        try {
            searchUrl += "&q=" + URLEncoder.encode(searchStr, "UTF-8");
        } catch (Exception e) {
            Log.e("MlRestClient", "Problema Armando URL", e);
        }

        MLApiAsyncTask task = new MLApiAsyncTask(this);
        task.execute(searchUrl);
    }

    public List<ItemDto> buildItemsList(String resp){
        List<ItemDto> items = new ArrayList<ItemDto>();

        try {
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
        } catch ( Exception e ){
            Log.e("MlRestClient", "Problema JSON", e);
        }

        callbacks.searchItemsDone( items );

        return items;
    }

    /*** GET ***/

    public void getItem(String itemId) {
        String getUrl = "https://api.mercadolibre.com/items/";
        try {
            getUrl += URLEncoder.encode(itemId, "UTF-8");
        } catch (Exception e) {
            Log.e("MlRestClient", "Problema Armando URL", e);
        }

        MLApiAsyncTask task = new MLApiAsyncTask(this);
        task.execute(getUrl);
    }

    public ItemDto buildItem(String resp){
        ItemDto item = null;

        try {
            JSONObject itemResult = new JSONObject(resp);

            item = new ItemDto(
                itemResult.getString("id"),
                itemResult.getString("title"),
                new BigDecimal( String.valueOf(itemResult.getDouble("price")) ),
                itemResult.getString("thumbnail"),
                itemResult.getJSONArray("pictures").getJSONObject(0).getString("url")
            );
        } catch ( Exception e ){
            Log.e("MlRestClient", "Problema JSON", e);
        }

        callbacks.getItemDone(item);

        return item;
    }


}
