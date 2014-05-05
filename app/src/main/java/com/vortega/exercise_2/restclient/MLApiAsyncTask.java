package com.vortega.exercise_2.restclient;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by vortega on 27/04/14.
 */
public class MLApiAsyncTask extends AsyncTask<String, Void, String> {

    AsyncTaskListener callbacks;

    public MLApiAsyncTask(MlRestClient wrapper) {
        this.callbacks = (AsyncTaskListener) wrapper;
    }

    public String doInBackground(String... itemStrs) {
        String apiUrl = itemStrs[0];
        HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(apiUrl);

        // Get the response
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String resp = "";

        try {
            resp = client.execute(request, responseHandler);
        } catch (Exception e) {
            Log.e("AsyncTask", "Problema Search", e);
        }

        return resp;
    }

    protected void onPostExecute(String items) {
        super.onPostExecute(items);
        callbacks.processResponse(items);
    }
}
