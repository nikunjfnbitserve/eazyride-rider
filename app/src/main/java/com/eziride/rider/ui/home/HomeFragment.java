package com.eziride.rider.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.eziride.rider.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener {


    MapView riderMap;
    GoogleMap map;
    EditText urLoc,dLoc;
    Button sbook;
    double lat,lang;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationProviderlient;

    Location currentLocation;
    LocationCallback mLocationCallback;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        urLoc=root.findViewById(R.id.h_yloc);
        dLoc=root.findViewById(R.id.h_dloc);
        sbook=root.findViewById(R.id.h_sbook);




        riderMap=root.findViewById(R.id.mapView);
        riderMap.onCreate(savedInstanceState);
        riderMap.getMapAsync(this);

        riderCurrentLocation();

        return root;
    }


    private void  riderCurrentLocation(){

        if(checkPermissions())
        {
            if(isLocationEnabled())
            {
                mFusedLocationProviderlient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        currentLocation=task.getResult();
                        if(currentLocation!=null)
                        { requestNewLocationData();
                        }
                        else{
                            lat=currentLocation.getLatitude();
                            lang=currentLocation.getLongitude();
                        }
                    }
                });

            }
            else{
                Toast.makeText(getActivity(), "Turn on Location", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else{
            requestForPermission();
        }

    }

    private void requestForPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ID);



    }

    private void requestNewLocationData() {


        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationProviderlient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationProviderlient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private boolean isLocationEnabled(){

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );


    }

    private boolean checkPermissions() {

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true;

        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        LatLng mylocation= new LatLng(lat,lang);

        map.clear();

        map.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,15));
        map.getUiSettings().setMyLocationButtonEnabled(true);
        //map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lang)));
    }


    @Override
    public void onResume() {
        riderMap.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        riderMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {

        riderMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        riderMap.onLowMemory();
        super.onLowMemory();
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