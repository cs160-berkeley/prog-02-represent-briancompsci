package com.example.brian.represent;

/**
 * Created by Brian on 3/1/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.view.View.*;

public class ListPoliticians extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageButton btn = (ImageButton)findViewById(R.id.imageButton);
        ImageButton btn2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton btn3 = (ImageButton)findViewById(R.id.imageButton3);

        btn.setBackgroundResource(R.drawable.jchu);
        btn2.setBackgroundResource(R.drawable.jchu);
        btn3.setBackgroundResource(R.drawable.jchu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String location = extras.getString("LOCATION");
            if (location != null) {
                if (location.equals("shake")) {
                    btn.setBackgroundResource(R.drawable.billperkins);
                    btn2.setBackgroundResource(R.drawable.billperkins);
                    btn3.setBackgroundResource(R.drawable.billperkins);
                    TextView textView1 = (TextView) findViewById(R.id.name);
                    TextView textView2 = (TextView) findViewById(R.id.name2);
                    TextView textView3 = (TextView) findViewById(R.id.name3);
                    TextView textView4 = (TextView) findViewById(R.id.party);
                    TextView textView5 = (TextView) findViewById(R.id.party2);
                    TextView textView6 = (TextView) findViewById(R.id.party3);
                    TextView textView7 = (TextView) findViewById(R.id.email);
                    TextView textView8 = (TextView) findViewById(R.id.email2);
                    TextView textView9 = (TextView) findViewById(R.id.email3);
                    TextView textView10 = (TextView) findViewById(R.id.website);
                    TextView textView11 = (TextView) findViewById(R.id.website2);
                    TextView textView12 = (TextView) findViewById(R.id.website3);
                    TextView textView13 = (TextView) findViewById(R.id.tweet);
                    TextView textView14 = (TextView) findViewById(R.id.tweet2);
                    TextView textView15 = (TextView) findViewById(R.id.tweet3);
                    textView1.setText("Bill Perkins");
                    textView2.setText("Bill Perkins");
                    textView3.setText("Bill Perkins");
                    textView4.setText("Democrat");
                    textView5.setText("Democrat");
                    textView6.setText("Democrat");
                    textView7.setText("billperkins@gmail.com");
                    textView8.setText("billperkins@gmail.com");
                    textView9.setText("billperkins@gmail.com");
                    textView10.setText("billperkins.com");
                    textView11.setText("billperkins.com");
                    textView12.setText("billperkins.com");
                    textView13.setText("I rock #rockus");
                    textView14.setText("I rock and am better than jchu #gome");
                    textView15.setText("bill perkins best perkins #perkins");

                }
            }
        }

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListPoliticians.this, DetailedPolitician.class));
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListPoliticians.this, DetailedPolitician.class));
            }
        });

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListPoliticians.this, DetailedPolitician.class));
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

