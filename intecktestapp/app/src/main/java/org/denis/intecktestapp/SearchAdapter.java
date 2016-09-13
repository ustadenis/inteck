package org.denis.intecktestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ustad on 13.09.2016.
 */
public class SearchAdapter extends ArrayAdapter<SearchResponse.SearchData.Track> {

    private Context mContext;

    public SearchAdapter(Context context) {
        super(context, R.layout.content_track_item);
        mContext = context;
    }

    public void updateDataset(SearchResponse data) {
        this.clear();
        this.addAll(data.getSearchData().getTracks());
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchResponse.SearchData.Track track = getItem(position);

        if (convertView == null) {
            int layoutID = (parent instanceof GridView) ? R.layout.content_track_item_1 : R.layout.content_track_item;
            convertView = LayoutInflater.from(getContext())
                    .inflate(layoutID, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView artist = (TextView) convertView.findViewById(R.id.tv_artist);

        artist.setText(track.mArtist);
        title.setText(track.mTitle);

        Glide.with(mContext)
                .load(track.mIcon)
                .fitCenter()
                .crossFade()
                .into(((ImageView) convertView.findViewById(R.id.iv_image)));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PlayerActivity.class);

                i.putExtra("artist", track.mArtist);
                i.putExtra("title", track.mTitle);
                i.putExtra("icon", track.mIcon);
                i.putExtra("previewUrl", track.mUrl);

                mContext.startActivity(i);
            }
        });

        return convertView;
    }
}
