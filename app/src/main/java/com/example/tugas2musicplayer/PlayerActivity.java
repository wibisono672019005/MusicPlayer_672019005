package com.example.tugas2musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    TextView judulLagu, waktuSekarang, waktuTotal, artistLagu;
    SeekBar mSeekBar;
    ImageView btnPause, btnNext, btnPrevious, logoMusic;

    ArrayList<ModelAudio> musicList;
    ModelAudio musicSekarang;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x = 0;

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
        artistLagu = findViewById(R.id.artistLagu);

        judulLagu.setSelected(true);

        musicList = (ArrayList<ModelAudio>) getIntent().getSerializableExtra("LIST");

        setResourceWithMusic();

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    waktuSekarang.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    if (mediaPlayer.isPlaying()) {
                        btnPause.setImageResource(R.drawable.ic_pause);
                        logoMusic.setRotation(x++);
                    } else {
                        btnPause.setImageResource(R.drawable.ic_play);
                        logoMusic.setRotation(0);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setResourceWithMusic() {
        musicSekarang = musicList.get(MyMediaPlayer.currentIndex);

        judulLagu.setText(musicSekarang.getTitle());
        artistLagu.setText(musicSekarang.getArtist());
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
        MyMediaPlayer.currentIndex += 1;
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