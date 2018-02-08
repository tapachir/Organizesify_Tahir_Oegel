package com.example.android.organizesifynewtry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent2) {
        String state = intent2.getExtras().getString("extra");

        Intent serviceIntent = new Intent (context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);
    }
}
