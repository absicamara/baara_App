package net.geeksh.baaraapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class Splash extends AppCompatActivity {

    VideoView splashVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        splashVideo = (VideoView) findViewById(R.id.splashVD);
        getSupportActionBar().hide();

        Uri vd = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splashvideo);
        splashVideo.setVideoURI(vd);

        splashVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(Splash.this, LoginActivity.class));
                finish();
            }
        });
        splashVideo.setZOrderOnTop(true);
        splashVideo.start();
    }
}
