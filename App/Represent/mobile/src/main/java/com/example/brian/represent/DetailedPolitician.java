package com.example.brian.represent;

/**
 * Created by Brian on 3/1/16.
 */
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.*;

public class DetailedPolitician extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent activityIntent = getIntent();
        int button = activityIntent.getIntExtra("BUTTON", 100);
//        String zipcode = activityIntent.getStringExtra("LOCATION");

//        String member = "http://congress.api.sunlightfoundation.com/legislators/locate?zip="
//                +zipcode+"&apikey=1c450f3a2fe7419f80cf8e1b460910ee";
        // Gets the URL from the UI's text field.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//            AsyncTask<String, Void, String> dwp = new DetailedWebPage(DetailedPolitician.this, this).execute(member);
            String bioguide = DownloadWebPage.all_info.get(button -1)[0];
            Log.d("T", bioguide);
            String committee = "http://congress.api.sunlightfoundation.com/committees?member_ids="
                    +bioguide+"&apikey=1c450f3a2fe7419f80cf8e1b460910ee";
            String bill = "http://congress.api.sunlightfoundation.com/bills?sponsor_id="
                    +bioguide+"&apikey=1c450f3a2fe7419f80cf8e1b460910ee";
            new DetailedWebPage2(getBaseContext(), this, button - 1).execute(committee);
            new DetailedWebPage3(getBaseContext(), this).execute(bill);
        }
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
