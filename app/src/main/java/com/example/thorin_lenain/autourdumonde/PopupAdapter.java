package com.example.thorin_lenain.autourdumonde;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thorin_lenain.autourdumonde.model.Restos;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Thorin_LeNain on 30/07/2014.
 */
class PopupAdapter implements GoogleMap.InfoWindowAdapter {

    private View myContentsView;
    private Context context;
    public ImageView ivIcon;

    PopupAdapter(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = inflater.inflate(R.layout.popup, null);
        this.context = context;
    }
        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
            ivIcon = ((ImageView)myContentsView.findViewById(R.id.icon));
            Log.e("id",marker.getId());
            String id = MapsActivity.hashMapMarkers.get(marker.getId());
            ivIcon.setImageResource((Integer) Restos.getInstance().getRestoById(id).getImage());

                return myContentsView;
        }

        @Override
        public View getInfoWindow(final Marker marker) {

            // TODO Auto-generated method stub
            return null;
        }
}