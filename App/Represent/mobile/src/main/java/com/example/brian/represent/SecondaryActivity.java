package com.example.brian.represent;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.fabric.sdk.android.Fabric;


public class SecondaryActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;
    private String mLatitudeText = "37.8717";
    private String mLongitudeText = "-122.2728";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        Button btn = (Button)findViewById(R.id.button2);

        Intent fromWatch = getIntent();
        if (fromWatch.getStringExtra("ZIPCODE") != null) {
            String zipcode = fromWatch.getStringExtra("ZIPCODE");
            Log.d("T", "so close");
            Intent activityIntent = new Intent(SecondaryActivity.this, ListPoliticians.class);
            activityIntent.putExtra("ZIPCODE", zipcode);
            Log.d("T", "le duh");
            startActivity(activityIntent);
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&sensor=true";
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new ZipToCounty(getBaseContext(), SecondaryActivity.this).execute(url);
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String zipcode = editText.getText().toString();
                Intent activityIntent = new Intent(SecondaryActivity.this, ListPoliticians.class);
                activityIntent.putExtra("ZIPCODE", zipcode);
                startActivity(activityIntent);
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&sensor=true";
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new ZipToCounty(getBaseContext(), SecondaryActivity.this).execute(url);
                }
            }
        });

        Button btn2 = (Button)findViewById(R.id.button);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.connect();
                Intent activityIntent = new Intent(SecondaryActivity.this, ListPoliticians.class);
                String[] coords = new String[2];
                coords[0] = mLatitudeText;
                coords[1] = mLongitudeText;
                activityIntent.putExtra("LOCATION", coords);
                startActivity(activityIntent);
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                        +coords[0]+","+coords[1]+"&key=AIzaSyBAx2X6YxMw8QHuliX0pBkv_9ZXFXN9PPc";
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new CoordToZip(getBaseContext(), SecondaryActivity.this).execute(url);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }
    //
    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation;
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {
    }
}
