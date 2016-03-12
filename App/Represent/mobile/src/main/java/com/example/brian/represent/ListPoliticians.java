package com.example.brian.represent;

/**
 * Created by Brian on 3/1/16.
 */
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static android.view.View.OnClickListener;

public class ListPoliticians extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent activityIntent = getIntent();
        if (activityIntent.hasExtra("ZIPCODE")) {
            final String zipcode = activityIntent.getStringExtra("ZIPCODE");
            String url = "http://congress.api.sunlightfoundation.com/legislators/locate?zip="
                    + zipcode + "&apikey=1c450f3a2fe7419f80cf8e1b460910ee";
            // Gets the URL from the UI's text field.
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadWebPage(ListPoliticians.this, this).execute(url);
            }
        } else {
            final String[] coord = activityIntent.getStringArrayExtra("LOCATION");
            String url = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" +
                   coord[0]+"&longitude="+coord[1]+"&apikey=1c450f3a2fe7419f80cf8e1b460910ee";
            // Gets the URL from the UI's text field.
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadWebPage(ListPoliticians.this, this).execute(url);
            }
        }

        ImageButton btn = (ImageButton)findViewById(R.id.imageButton);
        ImageButton btn2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton btn3 = (ImageButton)findViewById(R.id.imageButton3);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(getBaseContext(), DetailedPolitician.class);
                activityIntent.putExtra("BUTTON", 1);
                startActivity(activityIntent);
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(getBaseContext(), DetailedPolitician.class);
                activityIntent.putExtra("BUTTON", 2);
                startActivity(activityIntent);
            }
        });

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(getBaseContext(), DetailedPolitician.class);
                activityIntent.putExtra("BUTTON", 3);
                startActivity(activityIntent);
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
}

