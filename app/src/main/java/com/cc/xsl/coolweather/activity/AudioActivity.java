package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;
import android.widget.Button;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;

import java.io.FileDescriptor;
import java.io.IOException;

public class AudioActivity extends BaseActivity {
    public static Intent getAction(Context context) {
        return new Intent(context, AudioActivity.class);
    }

    private SoundPool soundPool;
    private MediaPlayer mediaPlayer0;
    private MediaPlayer mediaPlayer1;
    private Button play0btn, pause0btn, resume0btn, stop0btn;
    private Button play1btn, pause1btn, resume1btn, stop1btn;
    private Button play2btn, pause2btn, resume2btn, stop2btn;
    private Button play3btn, pause3btn, resume3btn, stop3btn;

    @Override
    protected void loadData() {
        initSoundPool();
//        initMediaPlayer();
    }

    private void initMediaPlayer() {
        initMediaPlayer0();
        initMediaPlayer1();
    }

    private void initMediaPlayer1() {
        mediaPlayer1 = MediaPlayer.create(this, R.raw.postscript);
        mediaPlayer1.setLooping(true);
        mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
//                play3btn.setEnabled(true);
            }
        });
    }

    private void initMediaPlayer0() {
        mediaPlayer0 = MediaPlayer.create(this, R.raw.handsome);
        mediaPlayer0.setLooping(true);
        mediaPlayer0.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtil.e("MediaPlayer onError: " + what + "\t" + extra);
                return false;
            }
        });
        mediaPlayer0.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.e("mediaPlayer0 onCompletion!");
            }
        });
        mediaPlayer0.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtil.e("mediaPlayer0 onPrepared!");
//                play2btn.setEnabled(true);
            }
        });
    }

    private int soundID0 = 0;
    private int soundID1 = 0;
    private int streamID0 = 0;
    private int streamID1 = 0;

    private float volumnRatio = 0;

    private void initSoundPool() {

        AudioManager am = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumnRatio = volumnCurrent / audioMaxVolumn;

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundID0 = soundPool.load(context, R.raw.yl2, 1);
        if (soundID0 == 0) {
            LogUtil.e("加载失败！");
        } else {
            LogUtil.e("加载成功！" + soundID0);
        }
        soundID1 = soundPool.load(context, R.raw.yl4, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                LogUtil.e("onLoadComplete: " + sampleId + "\t" + status);
                if (sampleId == soundID0) {
                    play0btn.setEnabled(true);
//                    streamID0 = soundPool.play(
//                            soundID0, volumnRatio, volumnRatio, 1, 9, 1
//                    );
                } else if (sampleId == soundID1) {
                    play1btn.setEnabled(true);
//                    streamID1 = soundPool.play(
//                            soundID1, volumnRatio, volumnRatio, 1, 9, 1
//                    );
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.unload(soundID0);
            soundPool.unload(soundID1);
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer0 != null) {
            if (mediaPlayer0.isPlaying()) {
                mediaPlayer0.stop();
            }
            mediaPlayer0.release();
            mediaPlayer0 = null;
        }
        if (mediaPlayer1 != null) {
            if (mediaPlayer1.isPlaying()) {
                mediaPlayer1.stop();
            }
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
    }

    @Override
    protected void initView() {
        play0btn = findViewById(R.id.play_0);
        play1btn = findViewById(R.id.play_1);
        resume0btn = findViewById(R.id.resume_0);
        resume1btn = findViewById(R.id.resume_1);
        pause0btn = findViewById(R.id.pause_0);
        pause1btn = findViewById(R.id.pause_1);
        stop0btn = findViewById(R.id.stop_0);
        stop1btn = findViewById(R.id.stop_1);

        play2btn = findViewById(R.id.play_2);
        play2btn.setEnabled(true);
        play3btn = findViewById(R.id.play_3);
        play3btn.setEnabled(true);
        resume2btn = findViewById(R.id.resume_2);
        resume3btn = findViewById(R.id.resume_3);
        pause2btn = findViewById(R.id.pause_2);
        pause3btn = findViewById(R.id.pause_3);
        stop2btn = findViewById(R.id.stop_2);
        stop3btn = findViewById(R.id.stop_3);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            // sound0
            case R.id.play_0: {
                if (soundID0 != 0) {
                    streamID0 = soundPool.play(
                            soundID0, volumnRatio, volumnRatio, 1, -1, 1
                    );
                    play0btn.setEnabled(false);
                    pause0btn.setEnabled(true);
                    resume0btn.setEnabled(false);
                    stop0btn.setEnabled(true);
                }
                break;
            }
            case R.id.pause_0: {
                if (streamID0 != 0) {
                    soundPool.pause(streamID0);
                    play0btn.setEnabled(false);
                    pause0btn.setEnabled(false);
                    resume0btn.setEnabled(true);
                    stop0btn.setEnabled(true);
//                    play0btn.setEnabled(true);
                }
                break;
            }
            case R.id.resume_0: {
                if (streamID0 != 0) {
                    soundPool.resume(streamID0);
                    play0btn.setEnabled(false);
                    pause0btn.setEnabled(true);
                    resume0btn.setEnabled(false);
                    stop0btn.setEnabled(true);
//                    play0btn.setEnabled(true);
                }
                break;
            }
            case R.id.stop_0: {
                if (streamID0 != 0) {
                    soundPool.stop(streamID0);
                    play0btn.setEnabled(true);
                    pause0btn.setEnabled(false);
                    resume0btn.setEnabled(false);
                    stop0btn.setEnabled(false);
                }
                break;
            }
            //sound1
            case R.id.play_1: {
                if (soundID1 != 0) {
                    streamID1 = soundPool.play(
                            soundID1, volumnRatio, volumnRatio, 1, -1, 1
                    );
                    play1btn.setEnabled(false);
                    pause1btn.setEnabled(true);
                    resume1btn.setEnabled(false);
                    stop1btn.setEnabled(true);
                }
                break;
            }
            case R.id.pause_1: {
                if (streamID1 != 0) {
                    soundPool.pause(streamID1);
                    play1btn.setEnabled(false);
                    pause1btn.setEnabled(false);
                    resume1btn.setEnabled(true);
                    stop1btn.setEnabled(true);
//                    play0btn.setEnabled(true);
                }
                break;
            }
            case R.id.resume_1: {
                if (streamID1 != 0) {
                    soundPool.resume(streamID1);
                    play1btn.setEnabled(false);
                    pause1btn.setEnabled(true);
                    resume1btn.setEnabled(false);
                    stop1btn.setEnabled(true);
//                    play0btn.setEnabled(true);
                }
                break;
            }
            case R.id.stop_1: {
                if (streamID1 != 0) {
                    soundPool.stop(streamID1);
                    play1btn.setEnabled(true);
                    pause1btn.setEnabled(false);
                    resume1btn.setEnabled(false);
                    stop1btn.setEnabled(false);
                }
                break;
            }
            // mediaPlayer0
            case R.id.play_2: {
//                try {
//                    mediaPlayer0.prepare();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                mediaPlayer0.reset();
//                mediaPlayer0 = MediaPlayer.create(
//                        this, R.raw.handsome
//                );
                initMediaPlayer0();
                mediaPlayer0.start();
                play2btn.setEnabled(false);
                pause2btn.setEnabled(true);
                resume2btn.setEnabled(false);
                stop2btn.setEnabled(true);
                break;
            }
            case R.id.pause_2: {
                if (mediaPlayer0.isPlaying()) {
                    mediaPlayer0.pause();
                    play2btn.setEnabled(false);
                    pause2btn.setEnabled(false);
                    resume2btn.setEnabled(true);
                    stop2btn.setEnabled(true);
                }
                break;
            }
            case R.id.resume_2: {
                mediaPlayer0.start();
                play2btn.setEnabled(false);
                pause2btn.setEnabled(true);
                resume2btn.setEnabled(false);
                stop2btn.setEnabled(true);
                break;
            }
            case R.id.stop_2: {
                mediaPlayer0.stop();
                mediaPlayer0.reset();
                mediaPlayer0.release();
                play2btn.setEnabled(true);
                pause2btn.setEnabled(false);
                resume2btn.setEnabled(false);
                stop2btn.setEnabled(false);
                break;
            }
            // mediaPlayer1
            case R.id.play_3: {
                initMediaPlayer1();
                mediaPlayer1.start();
                play3btn.setEnabled(false);
                pause3btn.setEnabled(true);
                resume3btn.setEnabled(false);
                stop3btn.setEnabled(true);
                break;
            }
            case R.id.pause_3: {
                if (mediaPlayer1.isPlaying()) {
                    mediaPlayer1.pause();
                    play3btn.setEnabled(false);
                    pause3btn.setEnabled(false);
                    resume3btn.setEnabled(true);
                    stop3btn.setEnabled(true);
                }
                break;
            }
            case R.id.resume_3: {
                mediaPlayer1.start();
                play3btn.setEnabled(false);
                pause3btn.setEnabled(true);
                resume3btn.setEnabled(false);
                stop3btn.setEnabled(true);
                break;
            }
            case R.id.stop_3: {
                mediaPlayer1.stop();
                mediaPlayer1.reset();
                mediaPlayer1.release();
                play3btn.setEnabled(true);
                pause3btn.setEnabled(false);
                resume3btn.setEnabled(false);
                stop3btn.setEnabled(false);
                break;
            }
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio;
    }
}
