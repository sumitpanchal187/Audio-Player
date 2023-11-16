package com.example.audioplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlayPause;
    private ImageView albumArtImageView;

    // Create a class to represent each audio track with its corresponding album art
    private static class AudioTrack {
        int audioResourceId;
        int albumArtResourceId;

        AudioTrack(int audioResourceId, int albumArtResourceId) {
            this.audioResourceId = audioResourceId;
            this.albumArtResourceId = albumArtResourceId;
        }
    }

    private AudioTrack[] audioTracks = {
            new AudioTrack(R.raw.a, R.drawable.img),
            new AudioTrack(R.raw.b, R.drawable.img_1),
            new AudioTrack(R.raw.c, R.drawable.img_2)
    };

    private int currentAudioIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, audioTracks[currentAudioIndex].audioResourceId);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnPlayPause.setText("Play");

        albumArtImageView = findViewById(R.id.albumArt);
        albumArtImageView.setImageResource(audioTracks[currentAudioIndex].albumArtResourceId);
    }

    public void onPreviousClick(View view) {
        if (currentAudioIndex > 0) {
            currentAudioIndex--;
        } else {
            currentAudioIndex = audioTracks.length - 1;
        }
        startPlaying();
    }

    public void onPlayPauseClick(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setText("Play");
        } else {
            mediaPlayer.start();
            btnPlayPause.setText("Pause");
        }
    }

    public void onNextClick(View view) {
        if (currentAudioIndex < audioTracks.length - 1) {
            currentAudioIndex++;
        } else {
            currentAudioIndex = 0;
        }
        startPlaying();
    }

    private void startPlaying() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, audioTracks[currentAudioIndex].audioResourceId);
        mediaPlayer.start();
        btnPlayPause.setText("Pause");
        albumArtImageView.setImageResource(audioTracks[currentAudioIndex].albumArtResourceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
