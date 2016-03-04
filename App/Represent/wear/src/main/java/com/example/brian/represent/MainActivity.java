package com.example.brian.represent;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements PageFragment.OnFragmentInteractionListener{
    private SensorManager mSensorManager;

    private ShakeListener mSensorListener;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

        final MainActivity _this = this;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeListener();

        mSensorListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                SwipeAdapter sa = (SwipeAdapter)viewPager.getAdapter();
                sa.areacode = "10027";
                sa.notifyDataSetChanged();
                viewPager.setAdapter(sa);
                Intent sendIntent = new Intent(_this, WatchToPhoneService.class);
                Log.d("T", "shakinggg");
                sendIntent.putExtra("LOCATION", "shake");
                startService(sendIntent);
            }
        });

//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            String location = extras.getString("LOCATION");
//            if (location == "90032") {
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
