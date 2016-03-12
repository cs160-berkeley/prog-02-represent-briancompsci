package com.example.brian.represent;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public class DetailedWebPage3 extends AsyncTask<String, Void, String> {

    Activity activity;
    int i;
    public DetailedWebPage3(Context context,Activity activity){
        this.context=context;
        this.activity=activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "oops";
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
        ArrayList<String> all_Bills = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        try {
            JSONObject JSONobj = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray jsonArray = JSONobj.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                childJSONObject = jsonArray.getJSONObject(i);

                if (childJSONObject.isNull("short_title")) {
                    all_Bills.add(childJSONObject.getString("official_title"));
                } else {
                    all_Bills.add(childJSONObject.getString("short_title"));
                }
                dates.add(childJSONObject.getString("introduced_on"));
            }
            TextView bills = (TextView) activity.findViewById(R.id.textView7);
            bills.setText("- "+dates.get(0) +": "+all_Bills.get(0)
                            +"\n- "+dates.get(1)+": "+all_Bills.get(1)+"\n- "+dates.get(2)
                            +": "+all_Bills.get(2));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
