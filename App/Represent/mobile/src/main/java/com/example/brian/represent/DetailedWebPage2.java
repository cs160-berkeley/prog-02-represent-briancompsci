package com.example.brian.represent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class DetailedWebPage2 extends AsyncTask<String, Void, String> {

    Activity activity;
    int i;
    public DetailedWebPage2(Context context,Activity activity, int i){
        this.context=context;
        this.activity=activity;
        this.i = i;
    }

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {
            Log.d("T", urls[0]);
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
        String[] goal = DownloadWebPage.all_info.get(i);
        JSONObject childJSONObject = null;
        ArrayList<String> comms = new ArrayList<>();
        try {
            JSONObject JSONobj = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray jsonArray = JSONobj.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                childJSONObject = jsonArray.getJSONObject(i);
                comms.add(childJSONObject.getString("name"));
            }
            TextView committee = (TextView) activity.findViewById(R.id.textView6);
            ImageView image = (ImageView) activity.findViewById(R.id.imageView);
            TextView politician = (TextView) activity.findViewById(R.id.textView3);
            TextView chamber = (TextView) activity.findViewById(R.id.textView10);
            TextView party = (TextView) activity.findViewById(R.id.textView4);
            TextView end_of_term = (TextView) activity.findViewById(R.id.textView5);
            LinearLayout layout = (LinearLayout) activity.findViewById(R.id.layout1);
            LinearLayout layout2 = (LinearLayout) activity.findViewById(R.id.layout2);

            Picasso.with(activity.getBaseContext()).load("https://theunitedstates.io/images/congress/225x275/"
                    +goal[0]+".jpg").into(image);

            chamber.setText(goal[3].substring(0, 1).toUpperCase() + goal[3].substring(1) + " Chamber");
            politician.setText(goal[1]);
            party.setText(goal[2]);
            if (goal[2].equals("Democrat")) {
                layout.setBackgroundColor(Color.parseColor("#336699"));
                layout2.setBackgroundColor(Color.parseColor("#336699"));
            } else if (goal[2].equals("Republican")) {
                layout.setBackgroundColor(Color.parseColor("#8c001a"));
                layout2.setBackgroundColor(Color.parseColor("#8c001a"));
            } else {
                layout.setBackgroundColor(Color.parseColor("#663399"));
                layout2.setBackgroundColor(Color.parseColor("#663399"));
            }
            end_of_term.setText(goal[4]);

            committee.setText("- "+comms.get(0)+"\n- "+comms.get(1)+"\n- "+comms.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
