package com.Ex.CarGame_part2.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.Ex.CarGame_part2.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrashSound {

    private final Context context;
    private final ExecutorService executorService;

    public CrashSound(Context context) {
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void playSound() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.crash);
                mediaPlayer.setLooping(false);
                mediaPlayer.setVolume(1.1f, 1.1f); // Ensure volume does not exceed 1.0 as it might not be supported
                mediaPlayer.start();

                // Ensure resources are released when playback is complete
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
        });
    }

    // Call this method to properly shut down the ExecutorService
    public void cleanup() {
        executorService.shutdown();
    }
}
