package com.superlamer.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView accuracyTextView;
    private TextView altitudeTextView;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        accuracyTextView = (TextView) findViewById(R.id.accuracyTextView);
        altitudeTextView = (TextView) findViewById(R.id.altitudeTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            Geocoder geocoder = null;
            List<Address> addresses = null;

            @Override
            public void onLocationChanged(Location location) {
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    Address currentLoc = null;
                    if (addresses != null && addresses.size() > 0) {
                        currentLoc = addresses.get(0);
                    }

                    if (currentLoc != null) {
                        latitudeTextView.setText(String.format(Locale.getDefault(),"%s: %f", "Latitude", location.getLatitude()));
                        longitudeTextView.setText(String.format(Locale.getDefault(),"%s: %f", "Longitude", location.getLongitude()));
                        accuracyTextView.setText(String.format(Locale.getDefault(),"%s: %f", "Accuracy", location.getAccuracy()));
                        altitudeTextView.setText(String.format(Locale.getDefault(),"%s: %f", "Altitude", location.getAltitude()));

                        if (currentLoc.getAddressLine(0) != null)
                            addressTextView.setText(String.format(Locale.getDefault(), "%s: %s", "Address", currentLoc.getAddressLine(0)));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
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


        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }
            }
        }
    }
}
