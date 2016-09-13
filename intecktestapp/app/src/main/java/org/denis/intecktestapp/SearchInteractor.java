package org.denis.intecktestapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ustad on 13.09.2016.
 */
public class SearchInteractor {

    private static final String TAG = SearchInteractor.class.getName();
    private static final String API_URL = "https://itunes.apple.com/search?term=";

    private OkHttpClient mClient;
    private SearchManager.SearchCallback mCallback;

    public SearchInteractor(SearchManager.SearchCallback callback) {
        mCallback = callback;
        mClient = new OkHttpClient();
    }

    public void request(final String term) {
        RequestAsyncTask task = new RequestAsyncTask();
        task.execute(term);
    }

    private class RequestAsyncTask extends AsyncTask {

        private JSONObject jsonObject;

        @Override
        protected Object doInBackground(Object[] params) {

            String url = API_URL + params[0];

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = mClient.newCall(request).execute();
                jsonObject = new JSONObject(response.body().string());
                if (jsonObject != null) {
                    Log.e(TAG, jsonObject.toString());
                    mCallback.onSuccess(new SearchResponse(jsonObject));
                }
            } catch (Exception e) {
                e.printStackTrace();
                mCallback.onFail();
            }

            return null;
        }
    }
}
