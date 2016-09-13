package org.denis.intecktestapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements TextWatcher, SearchManager.SearchCallback {

    private static final String TAG = MainActivityFragment.class.getName();

    private SearchManager mSearchManager;
    private SearchAdapter mSearchAdapter;

    private ListView mListRecyclerView;
    private GridView mListGridView;
    private EditText mSearchEdit;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListRecyclerView = (ListView) view.findViewById(R.id.listContentRecyclerView);
        mSearchEdit = (EditText) view.findViewById(R.id.searchRequestEditText);
        mListGridView = (GridView) view.findViewById(R.id.listContentGridView);

        mSearchManager = new SearchManager(this);
        mSearchAdapter = new SearchAdapter(getContext());

        mListRecyclerView.setAdapter(mSearchAdapter);
        mListGridView.setAdapter(mSearchAdapter);

        mSearchEdit.addTextChangedListener(this);

        if(getScreenOrientation() == 0) {
            mListGridView.setNumColumns(2);
        } else if(getScreenOrientation() == 1) {
            mListGridView.setNumColumns(3);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() >= 5) {
            mSearchManager.requestSearch(s.toString());
        }
    }

    public void changeView() {
        if (mListRecyclerView.getVisibility() == View.GONE) {
            mListRecyclerView.setVisibility(View.VISIBLE);
            mListGridView.setVisibility(View.GONE);
        } else {
            mListRecyclerView.setVisibility(View.GONE);
            mListGridView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(final SearchResponse response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSearchAdapter.updateDataset(response);
            }
        });
    }

    @Override
    public void onFail() {
        Log.e(TAG, "onFail()");
    }

    private int getScreenOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return 0;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 1;
        else
            return -1;
    }
}
