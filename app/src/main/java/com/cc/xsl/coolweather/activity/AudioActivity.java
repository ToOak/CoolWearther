package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.widget.Button;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;

public class AudioActivity extends BaseActivity {
    public static Intent getAction(Context context) {
        return new Intent(context, AudioActivity.class);
    }

    private SoundPool soundPool;
    private Button play0btn, pause0btn, resume0btn, stop0btn;
    private Button play1btn, pause1btn, resume1btn, stop1btn;

    @Override
    protected void loadData() {
        initSoundPool();
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
        soundPool.unload(soundID0);
        soundPool.unload(soundID1);
        soundPool.release();
        soundPool = null;
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

    }

    public void onClick(View view) {
        switch (view.getId()) {
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
