package com.practice.gmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.fragments.HomeFragment;
import com.practice.gmusic.fragments.LibraryFragment;
import com.practice.gmusic.fragments.MoreFragment;
import com.practice.gmusic.fragments.PlaylistFragment;
import com.practice.gmusic.handletrack.TrackService;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long pressTime;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    RelativeLayout rlTrack;
    RelativeLayout rlHaveConnect,rlNoConnect;
    TextView tvTrackName,tvTrackAuthor;
    ImageView ivPrev,ivPlayOrPause,ivNext,imgIcon;
    private ArrayList<Track> listTrack;
    private int position,trackCurrentPos,trackDuration;
    private boolean isTrackPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());
        getSupportActionBar().hide();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiveData,new IntentFilter(Const.serviceData));

        bottomNavigationView = findViewById(R.id.bnvDirect);
        rlTrack = findViewById(R.id.rlTrack);
        tvTrackName = findViewById(R.id.tvTrackName);
        tvTrackAuthor = findViewById(R.id.tvTrackAuthor);
        ivPrev = findViewById(R.id.ivPrevTrack);
        ivPlayOrPause = findViewById(R.id.ivPlayOrPause);
        rlHaveConnect = findViewById(R.id.rlHaveConnect);
        rlNoConnect = findViewById(R.id.rlNoConnect);
        ivNext = findViewById(R.id.ivNextTrack);
        imgIcon = findViewById(R.id.imgIcon);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.iHome:
                        fragment = new HomeFragment();
                        break;
                    case R.id.iLibrary:
                        fragment = new LibraryFragment();
                        break;
                    case R.id.iPlaylist:
                        fragment = new PlaylistFragment();
                        break;
                    case R.id.iMore:
                        fragment = new MoreFragment();
                        break;
                }
                replaceFragment(fragment);
                return false;
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Track track = (Track) bundle.getSerializable(Const.keyTrack);
            boolean isPlay = bundle.getBoolean(Const.keyIsPlay);
            position = bundle.getInt(Const.keyPosition);
            rlTrack.setVisibility(View.VISIBLE);
            renderDataToLayout(track, isPlay);
        }

    }

    private BroadcastReceiver receiveData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.serviceData)){
                Bundle bundle = intent.getExtras();
                if (bundle !=null){
                    listTrack = (ArrayList<Track>) bundle.getSerializable(Const.keyListTrack);
                    isTrackPlaying = bundle.getBoolean(Const.keyIsPlay);
                    position = bundle.getInt(Const.keyPosition);
                    int action = bundle.getInt(Const.keyAction);
                    Track track = listTrack.get(position);

                    if (action == Const.startAction){
                        handleTrackLayout(track,true,action);
                    }else{
                        handleTrackLayout(track,isTrackPlaying,action);
                    }
                }

            }
        }
    };

    private void handleTrackLayout(Track track,boolean isPlaying, int action){
        switch (action){
            case Const.startAction:
                rlTrack.setVisibility(View.VISIBLE);
            case Const.prevAction:
            case Const.pauseAction:
            case Const.playAction:
            case Const.nextAction:
                renderDataToLayout(track,isPlaying);
                break;
            case Const.exitAction:
                rlTrack.setVisibility(View.GONE);
                break;
        }
    }

    private void renderDataToLayout(Track track,boolean isPlaying){
        if (track != null){
            Picasso.get().load(track.getTrackImg())
                    .resize(200,200)
                    .centerCrop()
                    .into(imgIcon);
            tvTrackName.setText(track.getTrackName());
            tvTrackAuthor.setText(track.getTrackAuthor());
        }

        if (isPlaying){
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
        }else{
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
        }

        ivPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    sendDataToService(position,Const.pauseAction);
                }else{
                    sendDataToService(position,Const.playAction);
                }
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToService(position,Const.nextAction);
            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToService(position,Const.prevAction);
            }
        });

    }

    private void sendDataToService(int position,int action){
        Intent intent = new Intent(this,TrackService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Const.keyPosition,position);
        bundle.putInt(Const.keyAction,action);
        intent.putExtras(bundle);
        startService(intent);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMain,fragment);
        transaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isNetWorkConnected(){
        ConnectivityManager connectManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectManager.getActiveNetworkInfo();
        if (network != null){
            if (network.isConnected()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(pressTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }else{
            Toast.makeText(getBaseContext(),"Press back again to exit !",Toast.LENGTH_SHORT).show();
        }
        pressTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetWorkConnected()){
            rlHaveConnect.setVisibility(View.VISIBLE);
            rlNoConnect.setVisibility(View.GONE);
        }else{
            rlNoConnect.setVisibility(View.VISIBLE);
            rlHaveConnect.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, TrackService.class);
            stopService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MainActivity.this, TrackService.class);
        stopService(intent);
    }
}