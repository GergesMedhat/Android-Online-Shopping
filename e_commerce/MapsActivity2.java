package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.e_commerce.databinding.ActivityMaps2Binding;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addresstext;
    LocationManager locManager;
    myLocationListener locListner;
    Button getLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        addresstext=(EditText)findViewById(R.id.editText);
        getLocation= (Button)findViewById(R.id.btn1);
        locListner=new myLocationListener(getApplicationContext());
        locManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try{
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,locListner);
        }
        catch (SecurityException ex)
        {
            Toast.makeText(getApplicationContext(),"you are not allowed to access the current location",Toast.LENGTH_SHORT).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;

                try{
                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException x){
                    Toast.makeText(getApplicationContext(),"you did not allow to access the current location",Toast.LENGTH_SHORT).show();
                }
                if(loc!=null){
                    LatLng myPosition= new LatLng(loc.getLatitude(),loc.getLongitude());
                    try{
                        addressList=coder.getFromLocation(myPosition.latitude,myPosition.longitude,1);
                        if(!addressList.isEmpty()){
                            String address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                                address+=addressList.get(0).getAddressLine(i)+", ";

                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(address)).setDraggable(true);
                            addresstext.setText(address);
                        }
                    }
                    catch (IOException e){
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));

                }
                else
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determined",Toast.LENGTH_SHORT).show();

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address>addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + ", ";
                        addresstext.setText(address);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Address for this location", Toast.LENGTH_SHORT).show();
                        addresstext.getText().clear();
                    }
                }
                catch (IOException e){
                    Toast.makeText(getApplicationContext(),"Can not get the address,Chech your network",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });


    }
}