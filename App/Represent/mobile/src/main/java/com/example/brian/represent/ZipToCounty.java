package com.example.brian.represent;

/**
 * Created by Brian on 3/10/16.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Brian on 3/8/16.
 */
public class ZipToCounty extends AsyncTask<String, Void, String> {

    static String[] voting_data1;
    Activity activity;
    public ZipToCounty(Context context,Activity activity){
        this.context=context;
        this.activity=activity;
        voting_data1 = new String[4];
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
        String targ_county = "";
        try {
            JSONArray data_file = new JSONArray(loadJSONFromAsset());

            JSONObject JSONobj = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray jsonArray = JSONobj.getJSONArray("results");
            childJSONObject = jsonArray.getJSONObject(0);
            JSONArray deepjsonArray = childJSONObject.getJSONArray("address_components");
            childJSONObject = deepjsonArray.getJSONObject(2);
            String county = childJSONObject.getString("short_name");
            childJSONObject = deepjsonArray.getJSONObject(3);
            String state = childJSONObject.getString("long_name");
            String[] county_arr = county.split(" ");
            int length = county_arr.length;
            if (county_arr[length-1].equals("County")) {
                for (int i=0; i<length-1; i++) {
                    if (i != length - 1) {
                        targ_county += county_arr[i] + " ";
                    } else {
                        targ_county += county_arr[i];
                    }
                }
            }
            targ_county = targ_county.trim();
            for (int i=0; i<data_file.length(); i++) {
                JSONObject j = (JSONObject) data_file.get(i);
                if (j.getString("county-name").equals(targ_county)) {
                    Log.d("T", j.getString("county-name"));
                    voting_data1[0] = state;
                    voting_data1[1] = county;
                    voting_data1[2] = j.getString("obama-percentage");
                    voting_data1[3] = j.getString("romney-percentage");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("election-county-2012.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}