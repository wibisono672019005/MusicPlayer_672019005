package com.example.tugas2musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    TextView judulLagu, waktuSekarang, waktuTotal;
    SeekBar mSeekBar;
    ImageView btnPause, btnNext, btnPrevious, logoMusic;

    ArrayList<ModelAudio> musicList;
    ModelAudio musicSekarang;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        judulLagu = findViewById(R.id.judulLagu);
        waktuSekarang = findViewById(R.id.waktuSekarang);
        waktuTotal = findViewById(R.id.waktuTotal);
        mSeekBar = findViewById(R.id.mSeekBarTime);
        btnPause = findViewById(R.id.btn_pause);
        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);
        logoMusic = findViewById(R.id.logoMusic);

        judulLagu.setSelected(true);

        musicList = (ArrayList<ModelAudio>) getIntent().getSerializableExtra("LIST");

        setResourceWithMusic();
    }

    void setResourceWithMusic() {
        musicSekarang = musicList.get(MyMediaPlayer.currentIndex);

        judulLagu.setText(musicSekarang.getTitle());
        waktuTotal.setText(convertToMMSS(musicSekarang.getDuration()));

        btnPause.setOnClickListener(view -> pauseMusic());
        btnNext.setOnClickListener(view -> playNextMusic());
        btnPrevious.setOnClickListener(view -> playPreviousMusic());

        playMusic();
    }

    private void playMusic() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicSekarang.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mSeekBar.setProgress(0);
            mSeekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playNextMusic() {
        if (MyMediaPlayer.currentIndex == musicList.size()-1) {
            return;
        }
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void playPreviousMusic() {
        if (MyMediaPlayer.currentIndex == 0) {
            return;
        }
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public static String convertToMMSS(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}