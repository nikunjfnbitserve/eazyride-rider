package com.eziride.rider;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapDemo extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    int PERMISSION_ID=44;
    MapView mapview;
    Location currentLocation;
    EditText pickupLocation,destinationLocation;
    double lat,lang;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    private long UPDATE_INTERVAL=2000;
    private long FASTEST_INTERVAL=5000;
    LatLng latLng;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_map_demo, container, false);

       pickupLocation=view.findViewById(R.id.h_yloc);
       destinationLocation=view.findViewById(R.id.h_dloc);
            mFusedLocationClient= LocationServices.getFusedLocationProviderClient(getActivity());

            fetchLocation();
          mapview=view.findViewById(R.id.mapView);
          mapview.onCreate(savedInstanceState);

          mapview.getMapAsync(this);
        //  mapview.onResume();

        pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i= new Intent(getActivity(),SearchPlaces.class);
                    startActivity(i);
            }
        });

        return view;

    }

    private void fetchLocation() {

        if(checkPermissions())
        {
            if(isLocationEnabled())
            {
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener( new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete( Task<Location> task) {

                                currentLocation=task.getResult();
                                if(currentLocation==null)
                                {
                                   requestNewLocationData();
                                }
                                else
                                {
                                    lat=currentLocation.getLatitude();
                                    lang=currentLocation.getLongitude();


                                }
                            }
                        });
            }
            else{

                Toast.makeText(getActivity(), "Turn on Location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else{

         requestPermission();

        }

    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ID);
    }
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setNumUpdates(5);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper());
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap=googleMap;


        LatLng rloc = new LatLng(lat,lang);

        mGoogleMap.clear();

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(rloc));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rloc,15));
            mGoogleMap.addMarker(new MarkerOptions().position(rloc).title("Marker in current location"));



    }

    @Override
    public void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
}
