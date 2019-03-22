package com.example.thecoffeehouse.store.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private View.OnClickListener windowInfoClickListener;

    public MapInfoWindow(Context context) {
        this.context = context;
    }

    public void setWindowInfoClickListener(View.OnClickListener windowInfoClickListener) {
        this.windowInfoClickListener = windowInfoClickListener;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_map_window_info, null);
        TextView title = view.findViewById(R.id.mapMarkerTitle);
        title.setText(marker.getTitle());
        TextView snip = view.findViewById(R.id.mapMarkerSnipset);
        snip.setText(marker.getSnippet());
        view.setOnClickListener(windowInfoClickListener);
        return view;
    }
}
