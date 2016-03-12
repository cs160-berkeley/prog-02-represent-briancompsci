package com.example.brian.represent;

/**
 * Created by Brian on 3/2/16.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        // Value contains the String we sent over in WatchToPhoneService, "good job"
        String button = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        Log.d("T", button);
        if (button.length() == 1) {
            Intent intent = new Intent(this, DetailedPolitician.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int index = Integer.parseInt(button);
            intent.putExtra("BUTTON", index);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SecondaryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ZIPCODE", button);
            Log.d("T", button);
            startActivity(intent);
        }

    }
}
