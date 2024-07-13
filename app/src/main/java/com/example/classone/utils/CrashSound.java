package com.example.classone.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.example.classone.R;

public class CrashSound extends AsyncTask<Void,Void,Void> {

    private Context context;

    public CrashSound(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this.context, R.raw.crash);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(1.0f,1.0f);
        mediaPlayer.start();
        return null;
    }
}
