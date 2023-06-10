package com.practice.gmusic.handletrack;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.practice.gmusic.MainActivity;
import com.practice.gmusic.R;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class TrackService extends Service{

    private ArrayList<Track> tempListTrack;
    private int position;
    private boolean isTrackPlaying = false;
    private Track currentTrack;
    private int action;
    private MediaPlayer trackPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        position = intent.getIntExtra(Const.keyPosition,-1);
        action = intent.getIntExtra(Const.keyAction,-1);
        if (bundle != null){
            ArrayList<Track> listTrack = (ArrayList<Track>) bundle.getSerializable(Const.keyListTrack);
            position = bundle.getInt(Const.keyPosition);
            action = bundle.getInt(Const.keyAction);
            if (listTrack != null) {
                tempListTrack = listTrack;
                currentTrack = tempListTrack.get(position);
                try {
                    onStartTrack(currentTrack);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showNotification(currentTrack);
            }
        }

        try {
            handleTrackAction(action);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    private void showNotification(Track track){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Const.keyIsPlay,isTrackPlaying);
        bundle.putSerializable(Const.keyTrack,track);
        intent.putExtras(bundle);
        PendingIntent pIntent = PendingIntent.getActivity(
                this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_track);
        remoteViews.setTextViewText(R.id.tvTrackName,track.getTrackName());
        remoteViews.setTextViewText(R.id.tvTrackAuthor,track.getTrackAuthor());
        remoteViews.setImageViewResource(R.id.ivPlayOrPause,R.drawable.ic_pause);

        if (isTrackPlaying){
            remoteViews.setOnClickPendingIntent(R.id.ivPlayOrPause,getPIntent(this,Const.pauseAction));
            remoteViews.setImageViewResource(R.id.ivPlayOrPause,R.drawable.ic_pause);
        }else{
            remoteViews.setOnClickPendingIntent(R.id.ivPlayOrPause,getPIntent(this,Const.playAction));
            remoteViews.setImageViewResource(R.id.ivPlayOrPause,R.drawable.ic_play);
        }

        remoteViews.setOnClickPendingIntent(R.id.ivNextTrack,getPIntent(this,Const.nextAction));
        remoteViews.setOnClickPendingIntent(R.id.ivPrevTrack,getPIntent(this,Const.prevAction));

        Notification trackNotification = new NotificationCompat.Builder(this, Const.channelId)
                .setSmallIcon(R.drawable.large_icon_app)
                .setCustomContentView(remoteViews)
                .setContentIntent(pIntent)
                .setSound(null)
                .build();

        Picasso.get().load(track.getTrackImg())
                .resize(200,200)
                .into(remoteViews,R.id.imgIcon,Const.notificationId,trackNotification);

        startForeground(Const.notificationId,trackNotification);
    }

    private PendingIntent getPIntent(Context context,int action){
        Intent intent = new Intent(this,TrackReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Const.keyPosition,position);
        bundle.putInt(Const.keyAction,action);
        intent.putExtras(bundle);

        return PendingIntent.getBroadcast(
                context.getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleTrackAction(int action) throws IOException {
        switch (action){
            case Const.prevAction:
                onPrevTrack();
                break;
            case Const.playAction:
                onPlayTrack();
                break;
            case Const.pauseAction:
                onPauseTrack();
                break;
            case Const.nextAction:
                onNextTrack();
                break;
        }
    }

    private void sendDataToActivity(int action){
        Intent intent = new Intent(Const.serviceData);
        Bundle bundle = new Bundle();
        bundle.putInt(Const.keyPosition,position);
        bundle.putInt(Const.keyAction,action);
        bundle.putBoolean(Const.keyIsPlay,isTrackPlaying);
        bundle.putSerializable(Const.keyListTrack,tempListTrack);
        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        trackPlayer.stop();
        trackPlayer.reset();
        trackPlayer.release();
        sendDataToActivity(Const.exitAction);
    }

    private void initPropTrack(String url) throws IOException {
        trackPlayer = new MediaPlayer();
        trackPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        trackPlayer.setDataSource(url);
        trackPlayer.prepareAsync();
        trackPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                trackPlayer.start();
                trackPlayer.setLooping(true);
            }
        });
    }

    private void onStartTrack(Track track) throws IOException {

        if (isTrackPlaying){
            trackPlayer.stop();
            trackPlayer.reset();
            trackPlayer.release();
            initPropTrack(track.getTrackUrl());
        }else{
            initPropTrack(track.getTrackUrl());
            isTrackPlaying = true;
        }

        sendDataToActivity(Const.startAction);
    }

    public void onPlayTrack() {
        if (trackPlayer != null && isTrackPlaying == false){
            trackPlayer.start();
            isTrackPlaying = true;
            showNotification(currentTrack);
            sendDataToActivity(Const.pauseAction);
        }
    }

    public void onPauseTrack() {
        if (trackPlayer != null && isTrackPlaying == true){
            trackPlayer.pause();
            isTrackPlaying = false;
            showNotification(currentTrack);
            sendDataToActivity(Const.pauseAction);
        }
    }

    public void onPrevTrack() throws IOException {
        position--;
        if (position < 0){
            position = tempListTrack.size() - 1;
        }
        currentTrack = tempListTrack.get(position);
        onStartTrack(currentTrack);
        showNotification(currentTrack);
        sendDataToActivity(Const.prevAction);
    }

    public void onNextTrack() throws IOException {
        position++;
        if (position > tempListTrack.size() - 1){
            position = 0;
        }
        currentTrack = tempListTrack.get(position);
        onStartTrack(currentTrack);
        showNotification(currentTrack);
        sendDataToActivity(Const.nextAction);
    }
}
