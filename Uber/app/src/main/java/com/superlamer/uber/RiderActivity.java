package com.superlamer.uber;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button requestBtn;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean isRequestActive;
    private Handler handler = new Handler();
    private TextView infoTextView;
    private Boolean driverActive = false;

    private void updateLocation(Location location) {

        if (!driverActive) {
            LatLng usrLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usrLoc, 16.0f));
            mMap.addMarker(new MarkerOptions().position(usrLoc).title("your locaiton"));
        }


    }

    private void checkForUpdates() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereExists("driverUsername");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    driverActive = true;


                    ParseQuery<ParseObject> driver = ParseQuery.getQuery("Driver");
                    driver.whereEqualTo("username", objects.get(0).getString("driverUsername"));
                    driver.setLimit(1);
                    driver.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null && objects.size() > 0) {

                                for (final ParseObject obj : objects) {
                                    ParseGeoPoint driverLocation = obj.getParseGeoPoint("location");

                                    Location lastKnownLocation = null;
                                    if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(RiderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    }

                                        if (lastKnownLocation != null) {
                                            ParseGeoPoint userLocation = new ParseGeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                                            Double distanceInMiles = driverLocation.distanceInMilesTo(userLocation);

                                            if (distanceInMiles < 0.01) {
                                                infoTextView.setText("Your driver is here");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        infoTextView.setText("");
                                                        requestBtn.setVisibility(View.VISIBLE);
                                                        requestBtn.setText("Call an uber");
                                                        isRequestActive = false;
                                                        driverActive = false;

                                                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
                                                        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

                                                        query.findInBackground(new FindCallback<ParseObject>() {
                                                            @Override
                                                            public void done(List<ParseObject> objects, ParseException e) {
                                                                if (e == null) {
                                                                    for (ParseObject object : objects) {
                                                                        object.deleteInBackground();
                                                                    }
                                                                }
                                                            }
                                                        });

                                                    }
                                                }, 5000);
                                            } else {

                                                Double distanceOneDP = (double) Math.round(distanceInMiles * 10) / 10;

                                                infoTextView.setText("Driver is " + String.valueOf(distanceOneDP) + " miles away!");

                                                LatLng driverLocatonLatLng = new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude());
                                                LatLng requestLocatonLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

                                                ArrayList<Marker> markers = new ArrayList<>();
                                                markers.clear();
                                                mMap.clear();

                                                markers.add(mMap.addMarker(new MarkerOptions().position(driverLocatonLatLng).title("Driver location")));
                                                markers.add(mMap.addMarker(new MarkerOptions().position(requestLocatonLatLng).title("Your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));


                                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                                for (Marker marker : markers) {
                                                    builder.include(marker.getPosition());
                                                }

                                                LatLngBounds bounds = builder.build();

                                                int padding = 100;
                                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                                                mMap.animateCamera(cu);

                                                requestBtn.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkForUpdates();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0, locationListener);
                Location lastKnownLocation  = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                updateLocation(lastKnownLocation);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoTextView = (TextView) findViewById(R.id.infoTextView);

        requestBtn = (Button) findViewById(R.id.requestBtn);

        ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("Request");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    isRequestActive = true;
                    requestBtn.setText("Cancel Uber");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkForUpdates();
                        }
                    }, 2000);

//                    for (ParseObject object : objects) {
//                        if (object.get("driverUsername") != null && !object.get("driverUsername").equals("") && object.get("driverUsername").toString().length() > 0) {
//
//                            userLocation = new LatLng(object.getParseGeoPoint("location").getLatitude(), object.getParseGeoPoint("location").getLongitude());
//
//                            ParseQuery<ParseObject> driver = ParseQuery.getQuery("Driver");
//                            driver.whereEqualTo("username", object.get("driverUsername"));
//                            driver.setLimit(1);
//                            driver.findInBackground(new FindCallback<ParseObject>() {
//                                @Override
//                                public void done(List<ParseObject> objects, ParseException e) {
//                                    if (e == null) {
//                                        for (ParseObject obj : objects) {
//                                            driverLocation = new LatLng(obj.getParseGeoPoint("location").getLatitude(), obj.getParseGeoPoint("location").getLongitude());
//
//                                            if (driverLocation != null) {
//                                                Thread thread = new Thread()  {
//                                                  @Override
//                                                  public void run() {
//                                                      try {
//                                                        while (!driverLocation.equals(userLocation)) {
//                                                            sleep(3000);
//                                                            updateMarkers();
//                                                        }
//                                                      } catch (InterruptedException e) {
//                                                          e.printStackTrace();
//                                                      }
//                                                  }
//                                                };
//
//                                                thread.start();
//                                            }
//                                        }
//                                    }
//                                }
//                            });
//                        }
//                    }

                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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

        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation  = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                updateLocation(lastKnownLocation);
            }
        }
    }

    public void callUber(View view) {
        Log.i("Info", "Call Uber");

        if (isRequestActive) {
            ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {
                        isRequestActive = false;
                        requestBtn.setText("Call an Uber");
                        for (ParseObject object : objects) {
                            object.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Toast.makeText(getApplicationContext(), "Cannot cancel request", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null) {
                    ParseObject request = new ParseObject("Request");
                    request.put("username", ParseUser.getCurrentUser().getUsername());

                    ParseGeoPoint parseGeoPoint = new ParseGeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    request.put("location", parseGeoPoint);
                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                requestBtn.setText("Cancel Uber");
                                isRequestActive = true;

                                checkForUpdates();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Could not find location, Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void logout(View view) {
        ParseUser.logOut();
        isRequestActive = false;

        if (!isRequestActive) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {
                        for (ParseObject object : objects) {
                            object.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Toast.makeText(getApplicationContext(), "Cannot cancel request", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Request cancelled", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            });

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
