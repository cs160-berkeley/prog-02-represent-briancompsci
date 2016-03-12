package com.example.brian.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Brian on 3/8/16.
 */
public class DownloadWebPage extends AsyncTask<String, Void, String> {

    static ArrayList<String[]> all_info;
    static String watch_info;
    Activity activity;
    public DownloadWebPage(Context context,Activity activity){
        this.context=context;
        this.activity=activity;
        all_info = new ArrayList<>();
        watch_info = "";
    }

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        URL url = new URL(myurl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }
    Context context;
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        JSONObject childJSONObject = null;
        try {
            JSONObject JSONobj = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray jsonArray = JSONobj.getJSONArray("results");
            for (int i = 0; i < 3; i++) {
                String[] info = new String[10];
                childJSONObject = jsonArray.getJSONObject(i);
                info[0] = childJSONObject.getString("bioguide_id");
                String targ_name = childJSONObject.getString("first_name")
                        + " " + childJSONObject.getString("last_name");
                String targ_party = childJSONObject.getString("party");
                String targ_chamber = childJSONObject.getString("chamber");
                String targ_eot = childJSONObject.getString("term_end");
                String targ_twitter = childJSONObject.getString("twitter_id");
                info[1] = targ_name;
                if (targ_party.equals("D")) {
                    targ_party = "Democrat";

                } else if (targ_party.equals("R")) {
                    targ_party = "Republican";
                } else {
                    targ_party = "Independent";
                }
                watch_info += "," + targ_chamber.substring(0, 1).toUpperCase() + targ_chamber.substring(1);
                watch_info += "," + targ_name;
                watch_info += "," + targ_party;
                info[2] = targ_party;
                info[3] = targ_chamber;
                info[4] = targ_eot;
                info[5] = targ_twitter;
                all_info.add(info);
                String targ_email = childJSONObject.getString("contact_form");
                String targ_website = childJSONObject.getString("website");
                if (i == 0) {
                    ImageButton btn = (ImageButton)activity.findViewById(R.id.imageButton);
                    LinearLayout ly = (LinearLayout)activity.findViewById(R.id.topPerson);
                    Picasso.with(activity.getBaseContext()).load("https://theunitedstates.io/images/congress/225x275/"
                            +info[0]+".jpg").into(btn);
                    TextView name = (TextView) activity.findViewById(R.id.name);
                    name.setText(targ_name);
                    TextView party = (TextView) activity.findViewById(R.id.party);
                    party.setText(targ_party);
                    if (targ_party.equals("Democrat")) {
                        ly.setBackgroundColor(Color.parseColor("#336699"));
                    } else if (targ_party.equals("Republican")){
                        ly.setBackgroundColor(Color.parseColor("#8c001a"));
                    } else {
                        ly.setBackgroundColor(Color.parseColor("#663399"));
                    }
                    TextView email = (TextView) activity.findViewById(R.id.email);
                    email.setClickable(true);
                    email.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href=" +"'" +targ_email+ "'" + ">Email</a>";
                    email.setText(Html.fromHtml(text));
                    TextView website = (TextView) activity.findViewById(R.id.website);
                    website.setClickable(true);
                    website.setMovementMethod(LinkMovementMethod.getInstance());
                    text = "<a href=" + "'" + targ_website + "'" + ">Website</a>";
                    website.setText(Html.fromHtml(text));
//                    TextView tweet = (TextView) activity.findViewById(R.id.tweet);
//                    tweet.setText()
                } else if (i == 1) {
                    ImageButton btn2 = (ImageButton)activity.findViewById(R.id.imageButton2);
                    LinearLayout ly2 = (LinearLayout)activity.findViewById(R.id.topPerson2);
                    Picasso.with(activity.getBaseContext()).load("https://theunitedstates.io/images/congress/225x275/"
                            +info[0]+".jpg").into(btn2);
                    TextView name2 = (TextView) activity.findViewById(R.id.name2);
                    name2.setText(targ_name);
                    TextView party2 = (TextView) activity.findViewById(R.id.party2);
                    party2.setText(targ_party);
                    if (targ_party.equals("Democrat")) {
                        ly2.setBackgroundColor(Color.parseColor("#336699"));
                    } else if (targ_party.equals("Republican")){
                        ly2.setBackgroundColor(Color.parseColor("#8c001a"));
                    } else {
                        ly2.setBackgroundColor(Color.parseColor("#663399"));
                    }
                    TextView email2 = (TextView) activity.findViewById(R.id.email2);
                    email2.setClickable(true);
                    email2.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href=" +"'" +targ_email+ "'" + ">Email</a>";
                    email2.setText(Html.fromHtml(text));
                    TextView website2 = (TextView) activity.findViewById(R.id.website2);
                    website2.setClickable(true);
                    website2.setMovementMethod(LinkMovementMethod.getInstance());
                    text = "<a href=" + "'" + targ_website + "'" + ">Website</a>";
                    website2.setText(Html.fromHtml(text));
//                    TextView tweet2 = (TextView) activity.findViewById(R.id.tweet2);
                } else {
                    ImageButton btn3 = (ImageButton)activity.findViewById(R.id.imageButton3);
                    LinearLayout ly3 = (LinearLayout)activity.findViewById(R.id.topPerson3);
                    Picasso.with(activity.getBaseContext()).load("https://theunitedstates.io/images/congress/225x275/"
                            +info[0]+".jpg").into(btn3);
                    TextView name3 = (TextView) activity.findViewById(R.id.name3);
                    name3.setText(targ_name);
                    TextView party3 = (TextView) activity.findViewById(R.id.party3);
                    party3.setText(targ_party);
                    if (targ_party.equals("Democrat")) {
                        ly3.setBackgroundColor(Color.parseColor("#336699"));
                    } else if (targ_party.equals("Republican")){
                        ly3.setBackgroundColor(Color.parseColor("#8c001a"));
                    } else {
                        ly3.setBackgroundColor(Color.parseColor("#663399"));
                    }
                    TextView email3 = (TextView) activity.findViewById(R.id.email3);
                    email3.setClickable(true);
                    email3.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href=" +"'" +targ_email+ "'" + ">Email</a>";
                    email3.setText(Html.fromHtml(text));
                    TextView website3 = (TextView) activity.findViewById(R.id.website3);
                    website3.setClickable(true);
                    website3.setMovementMethod(LinkMovementMethod.getInstance());
                    text = "<a href=" + "'" + targ_website + "'" + ">Website</a>";
                    website3.setText(Html.fromHtml(text));
//                    TextView tweet3 = (TextView) activity.findViewById(R.id.tweet3);
                }
            }
            if (ZipToCounty.voting_data1 != null) {
                watch_info += "," + ZipToCounty.voting_data1[0];
                watch_info += "," + ZipToCounty.voting_data1[1];
                watch_info += "," + ZipToCounty.voting_data1[2];
                watch_info += "," + ZipToCounty.voting_data1[3];
                ZipToCounty.voting_data1[0] = null;
                ZipToCounty.voting_data1[1] = null;
                ZipToCounty.voting_data1[2] = null;
                ZipToCounty.voting_data1[3] = null;
            } else if (CoordToZip.voting_data != null) {
                watch_info += "," + CoordToZip.voting_data[0];
                watch_info += "," + CoordToZip.voting_data[1];
                watch_info += "," + CoordToZip.voting_data[2];
                watch_info += "," + CoordToZip.voting_data[3];
                CoordToZip.voting_data[0] = null;
                CoordToZip.voting_data[1] = null;
                CoordToZip.voting_data[2] = null;
                CoordToZip.voting_data[3] = null;
            }

            Intent sendIntent = new Intent(activity, PhoneToWatchService.class);
            sendIntent.putExtra("INFO", watch_info);
            activity.startService(sendIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
