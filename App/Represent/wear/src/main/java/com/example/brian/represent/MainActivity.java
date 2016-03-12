package com.example.brian.represent;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends FragmentActivity implements PageFragment.OnFragmentInteractionListener, SensorEventListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Trb5QLaCdyChFOo2tPLxzIzDO";
    private static final String TWITTER_SECRET = "C0YriPBUTfCb9fgIYlL65OLjAR28VtLvDMSGkJaipDEZYRfSw9";

    ViewPager viewPager;
    private static boolean change = true;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private ArrayList<String> zips = new ArrayList<>();

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double)(x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                Toast toast = Toast.makeText(getBaseContext(), "Shake!", Toast.LENGTH_SHORT);
                toast.show();
                int index = (int)(Math.random()*15);
                String randomZip = zips.get(index);
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("ZIPCODE", randomZip);
                Log.d("T", randomZip);
                startService(sendIntent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        zips.add("27516");
        zips.add("31313");
        zips.add("48146");
        zips.add("91784");
        zips.add("33308");
        zips.add("78213");
        zips.add("18901");
        zips.add("34221");
        zips.add("18052");
        zips.add("48192");
        zips.add("49120");
        zips.add("07054");
        zips.add("61821");
        zips.add("51106");
        zips.add("02124");
        Intent receivedIntent = getIntent();
        if (receivedIntent.getStringExtra("INFO") != null) {
            String info = receivedIntent.getStringExtra("INFO");
            String[] relevant = info.split(",");
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), relevant);
            viewPager.setAdapter(swipeAdapter);
        }

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
