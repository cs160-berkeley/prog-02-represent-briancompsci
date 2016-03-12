package com.example.brian.represent;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Brian on 3/1/16.
 */

public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();
        //and actually connect it
        mWatchApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {
//        Bundle extras = intent.getExtras();
//        final String location = extras.getString("LOCATION");
//        sendMessage("/" + location, location);
//        Log.d("T", location);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        final Service _this = this;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras.getString("BUTTON") != null) {
                Log.d("T", "oh noes");
                final String button = extras.getString("BUTTON");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //first, connect to the apiclient
                        mWatchApiClient.connect();
                        //now that you're connected, send a massage with the cat name
                        sendMessage("BUTTON", button);
                        _this.stopSelf();
                    }
                }).start();
            } else {
                final String zipcode = extras.getString("ZIPCODE");
                Log.d("T", zipcode);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //first, connect to the apiclient
                        mWatchApiClient.connect();
                        //now that you're connected, send a massage with the cat name
                        sendMessage("ZIPCODE", zipcode);
                        _this.stopSelf();
                    }
                }).start();
            }

            // Send the message with the cat name
        }

        return START_STICKY;
    }

    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        final Service _this = this;
        Log.d("T", "in onconnected");
        final Bundle _bundle = bundle;
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        Log.d("T", "found nodes");
                        //when we find a connected node, we populate the list declared above
                        //finally, we can send a message
                        _this.stopSelf();

                    }
                });
    }

    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

    private void sendMessage(final String path, final String text ) {
        for (Node node : nodes) {
            Wearable.MessageApi.sendMessage(
                    mWatchApiClient, node.getId(), path, text.getBytes());
        }
    }

}