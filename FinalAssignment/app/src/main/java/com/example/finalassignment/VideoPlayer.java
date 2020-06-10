package com.example.finalassignment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;
import java.net.URL;

public class VideoPlayer extends AppCompatActivity{
    private static final String TAG = "video:";
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Uri uri = Uri.parse(getIntent().getStringExtra("videoUrl"));
        Log.d(TAG, "" + uri);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                else
                    videoView.start();
            }
        });
    }
}
