package id.ac.umn.musicplayer_vincentdiamond_27047;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static id.ac.umn.musicplayer_vincentdiamond_27047.PlaylistActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity {
    TextView songName, artistName, durationPlayed, durationTotal;
    ImageView albumArt, nextBtn, prevBtn;
    FloatingActionButton playPauseBtn;
    SeekBar progressSb;
    int position = -1;
    ArrayList<MusicFiles> listOfSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setTitle("Now Playing");
        initViews();
        getIntentMethod();
        songName.setText(listOfSongs.get(position).getTitle());
        if (!listOfSongs.get(position).getArtist().equals("<unknown>")) {
            artistName.setText(listOfSongs.get(position).getArtist());
        } else {
            artistName.setText(R.string.unknown_artist);
        }
        progressSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    progressSb.setProgress(mCurrentPosition);
                    durationPlayed.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }


    private void playThreadBtn() {
        playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        } else {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listOfSongs.size());
            uri = Uri.parse(listOfSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            getMetadata(uri);
            songName.setText(listOfSongs.get(position).getTitle());
            if (!listOfSongs.get(position).getArtist().equals("<unknown>")) {
                artistName.setText(listOfSongs.get(position).getArtist());
            } else {
                artistName.setText(R.string.unknown_artist);
            }
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listOfSongs.size());
            uri = Uri.parse(listOfSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            getMetadata(uri);
            songName.setText(listOfSongs.get(position).getTitle());
            if (!listOfSongs.get(position).getArtist().equals("<unknown>")) {
                artistName.setText(listOfSongs.get(position).getArtist());
            } else {
                artistName.setText(R.string.unknown_artist);
            }
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_play);
        }
    }

    private void prevThreadBtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listOfSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listOfSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            getMetadata(uri);
            songName.setText(listOfSongs.get(position).getTitle());
            if (!listOfSongs.get(position).getArtist().equals("<unknown>")) {
                artistName.setText(listOfSongs.get(position).getArtist());
            } else {
                artistName.setText(R.string.unknown_artist);
            }
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listOfSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listOfSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            getMetadata(uri);
            songName.setText(listOfSongs.get(position).getTitle());
            if (!listOfSongs.get(position).getArtist().equals("<unknown>")) {
                artistName.setText(listOfSongs.get(position).getArtist());
            } else {
                artistName.setText(R.string.unknown_artist);
            }
            progressSb.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        progressSb.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_play);
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listOfSongs = musicFiles;
        if (listOfSongs != null) {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listOfSongs.get(position).getPath());
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        progressSb.setMax(mediaPlayer.getDuration() / 1000);
        getMetadata(uri);
    }

    private void initViews() {
        songName = (TextView) findViewById(R.id.activity_player_tv_song);
        artistName = (TextView) findViewById(R.id.activity_player_tv_artist);
        durationPlayed = (TextView) findViewById(R.id.activity_player_tv_duration_played);
        durationTotal = (TextView) findViewById(R.id.activity_player_tv_duration_total);
        albumArt = (ImageView) findViewById(R.id.activity_player_iv_album_art);
        nextBtn = (ImageView) findViewById(R.id.activity_player_iv_next);
        prevBtn = (ImageView) findViewById(R.id.activity_player_iv_prev);
        playPauseBtn = (FloatingActionButton) findViewById(R.id.activity_player_fab_play_pause);
        progressSb = (SeekBar) findViewById(R.id.activity_player_sb_progress);
    }

    private void getMetadata(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int _durationTotal = Integer.parseInt(listOfSongs.get(position).getDuration()) / 1000;
        durationTotal.setText(formattedTime(_durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(albumArt);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.album_art_placeholder)
                    .into(albumArt);
        }
    }
}