package com.dewey.music_video_526;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;

//使用Service播放音乐
public class MusicService extends Service{
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public static int isReturnTo = 0;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static ObjectAnimator animator;
    public MusicService() {
        initMediaPlayer();

    }

    public void initMediaPlayer() {
        try {
            //String file_path = "/storage/0123-4567/K.Will-Melt.mp3";
            String path="http://sc1.111ttt.cn:8282/2018/1/03m/13/396131229550.m4a?tflag=1519095601&pin=6cd414115fdb9a950d827487b16b5f97#.mp3";
//            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/K.Will-Melt.mp3";
//            //String file_path = "/data/K.Will-Melt.mp3";
//            mediaPlayer.setDataSource(file_path);
            mediaPlayer.setDataSource( path );
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);  // 设置循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public  void AnimatorAction() {
        if (mediaPlayer.isPlaying()) {
            animator.setDuration(5000);
            animator.setInterpolator(new LinearInterpolator()); // 均速旋转
            animator.setRepeatCount( ValueAnimator.INFINITE); // 无限循环
            animator.setRepeatMode(ValueAnimator.INFINITE);
            animator.start();
        }
    }
    private int flag = 0;
    public static String which = "";
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void playOrPause() {
        flag++;
        if (flag >= 1000) flag = 2;

        which = "pause";

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            animator.pause();
        } else {
            mediaPlayer.start();

            if ((flag == 1) || (isReturnTo == 1)) {
                animator.setDuration(5000);
                animator.setInterpolator(new LinearInterpolator()); // 均速旋转
                animator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
                animator.setRepeatMode(ValueAnimator.INFINITE);
                animator.start();
            } else {
                animator.resume();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void stop() {
        which = "stop";
        animator.pause();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
    /**
     * onBind 是 Service 的虚方法，因此我们不得不实现它。
     * 返回 null，表示客服端不能建立到此服务的连接。
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
