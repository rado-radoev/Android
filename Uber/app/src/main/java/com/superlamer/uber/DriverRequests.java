package com.superlamer.uber;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class DriverRequests extends AppCompatActivity {

    private GoogleMap mMap;
    private ArrayList<String> requests;
    private ArrayAdapter arrayAdapter;
    private ListView driverRequests;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private void updateLocation(Location location) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_requests);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
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
        };



        requests = new ArrayList<String>();
        requests.add("test");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requests);

        driverRequests = (ListView) findViewById(R.id.driverRequestsLayout);
        driverRequests.setAdapter(arrayAdapter);

    }

    private void gatherRequests() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
    }
}
