package com.bigct.appint.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.bigct.appint.R;

/**
 * Created by sdragon on 2/27/2016.
 */
public class SoundPlayer {
    public static Context mContext;

    public static void open(Context context) {
        mContext = context;
    }

    public static void playBeep() {
        try {
            MediaPlayer player = MediaPlayer.create(mContext, R.raw.beep);
            player.start();
        }catch(Exception e) {

        }
    }
}
