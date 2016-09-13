package org.denis.intecktestapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ustad on 13.09.2016.
 */
public class SearchResponse {

    private SearchData mSearchData;

    private JSONObject jsonResponse = null;

    private int resultCount = 0;

    public SearchResponse(JSONObject response) {
        try {
            resultCount = response.getInt("resultCount");
            jsonResponse = response;

            mSearchData = new SearchData(jsonResponse.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getResultCount() {
        return resultCount;
    }

    public SearchData getSearchData() {
        return mSearchData;
    }

    public static class SearchData {

        public static class Track {
            String mTitle;

            String mArtist;

            String mIcon;

            String mUrl;

            public Track(String title, String artist, String icon, String url) {
                mTitle = title;
                mArtist = artist;
                mIcon = icon;
                mUrl = url;
            }
        }

        private List<Track> mTracks = new ArrayList<>();

        private JSONArray mJSONTracks = null;

        private SearchData(JSONArray array) {
            mJSONTracks = array;

            for(int i = 0; i < mJSONTracks.length(); i++) {
                try {
                    JSONObject track = mJSONTracks.getJSONObject(i);

                    mTracks.add(new Track(track.getString("trackName"), track.getString("artistName"), track.getString("artworkUrl100"), track.getString("previewUrl")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        public List<Track> getTracks() {
            return mTracks;
        }
    }
}
