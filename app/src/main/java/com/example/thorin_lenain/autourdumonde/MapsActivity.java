package com.example.thorin_lenain.autourdumonde;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thorin_lenain.autourdumonde.model.Restos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapsActivity extends Fragment implements LocationListener{
    public static HashMap<String, String> hashMapMarkers = new HashMap<String, String>();
    public static GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static Location location;
    public String adress = null;
    private View view;
    private LocationManager locationManager;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);

        location = getLocation();
        if(location == null) {
            location.setLatitude(latitude);
            location.setLongitude(longitude);
        }
        // Get the location manager
        setUpMap();
        Log.e("location", String.valueOf(location));

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {

            final Double lat = getActivity().getIntent().getDoubleExtra("LAT", 0);
            final Double lon = getActivity().getIntent().getDoubleExtra("LON",0);

            LatLng l2 = new LatLng(lat,lon);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f));

            //final String editArrivee = getActivity().getIntent().getStringExtra("ARRIVEE");
            LatLng l = new LatLng(location.getLatitude(),location.getLongitude());
            new ItineraireTask(getActivity(), mMap, l, l2).execute();
            //Log.e("adress final",editArrivee);
            MainActivity.pager.setCurrentItem(1);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.e("ID",MapsActivity.hashMapMarkers.get(marker.getId()).toString());
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailMenuActivity.class);
                intent.putExtra("EXTRA_ID", MapsActivity.hashMapMarkers.get(marker.getId()).toString());
                startActivity(intent);
            }
        });
        return view;
    }


    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if(mMap != null)

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); // A ne pas oublier!!
            mMap.setMyLocationEnabled(true);

            mMap.setInfoWindowAdapter(new PopupAdapter(getActivity()));
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        location = locationManager.getLastKnownLocation(provider);

        for(int i = 0; i< Restos.getInstance().getRestos().size(); i++)
        {
            Log.e("List", String.valueOf(i));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(Restos.getInstance().getRestos().get(i).getLatLng())
                    .title(Restos.getInstance().getRestos().get(i).getTitre())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_mdpi))
                    .snippet(Restos.getInstance().getRestos().get(i).getAdresse()));
            hashMapMarkers.put(marker.getId(),Restos.getInstance().getRestos().get(i).getId());
        }
    }
    public Location getLocation() {
        try {
            locationManager = (LocationManager) getActivity()
                    .getSystemService(getActivity().LOCATION_SERVICE);

            // getting GPS status
            Boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            Boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                //getActivity().canGetLocation = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            10000,
                            0, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                10000,
                                0, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
