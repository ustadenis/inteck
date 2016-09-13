package org.denis.intecktestapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/**
 * Created by ustad on 13.09.2016.
 */
public class SearchManager {

    private static final String TAG = SearchManager.class.getName();

    private SearchInteractor mSearchInteractor;

    public SearchManager(SearchCallback callback) {
        mSearchInteractor = new SearchInteractor(callback);
    }

    public void requestSearch(String request) {
        mSearchInteractor.request(request);
    }

    public interface SearchCallback {
        void onSuccess(SearchResponse response);
        void onFail();
    }
}
