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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView image = (ImageView)findViewById(R.id.imageView);
        TextView politician = (TextView) findViewById(R.id.textView3);
        TextView party = (TextView) findViewById(R.id.textView4);
        TextView end_of_term = (TextView) findViewById(R.id.textView5);
        TextView committee = (TextView) findViewById(R.id.textView6);
        TextView bills = (TextView) findViewById(R.id.textView7);

        image.setBackgroundResource(R.drawable.jchu);
        politician.setText("House Representative: Judy Chu");
        party.setText("Democratic Party");
        end_of_term.setText("End of Term: 1/03/17");
        committee.setText("Committees: Judiciary and Small Business");
        bills.setText("Recent Bills Sponsored: Transparency in Small Business Goaling Act of 2016. Introduced on 1/06/16");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String locate = extras.getString("LOCATION");
            if (locate != null) {
                Log.d("T", locate);
                if (locate.equals("10027")) {
                    image.setBackgroundResource(R.drawable.billperkins);
                    politician.setText("House Representative: Bill Perkins");
                    party.setText("Democratic Party");
                    end_of_term.setText("End of Term: 12/31/16");
                    committee.setText("Committees: Judiciary and Transportation");
                    bills.setText("Recent Bills Sponsored: Bill relating to minimum wage. Introduced on 2/29/16");
                }
            }

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
