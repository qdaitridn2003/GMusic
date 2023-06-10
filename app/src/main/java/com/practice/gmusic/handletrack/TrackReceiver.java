package com.practice.gmusic.handletrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.practice.gmusic.others.Const;


public class TrackReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt(Const.keyPosition);
        int action = bundle.getInt(Const.keyAction);

        Intent intentBr = new Intent(context,TrackService.class);
        intentBr.putExtra(Const.keyPosition,position);
        intentBr.putExtra(Const.keyAction,action);

        context.startService(intentBr);
    }
}
