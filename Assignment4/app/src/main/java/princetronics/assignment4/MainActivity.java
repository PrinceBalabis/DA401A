package princetronics.assignment4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvSongTitle, tvStatus;

    private MyMediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MyMediaPlayer(this);

        viewsInit();
        buttonListenersInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Draw view
    void viewsInit() {
        tvSongTitle = (TextView) findViewById(R.id.tv_songtitle);
        tvStatus = (TextView) findViewById(R.id.tv_status);

        tvSongTitle.setText(mediaPlayer.getCurrentSongTitle()); // draw song title in app
        tvStatus.setText("Playing"); // Update media player status
    }

    // Initialize button listeners
    void buttonListenersInit() {
        Button btnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        View.OnClickListener oclBtnPlayPause = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Play/pause button pressed!");
                togglePlayPause();
            }
        };
        btnPlayPause.setOnClickListener(oclBtnPlayPause);

        Button btnPrevious = (Button) findViewById(R.id.btn_previous);
        View.OnClickListener oclBtnPrevious = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousSong();
            }
        };
        btnPrevious.setOnClickListener(oclBtnPrevious);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        View.OnClickListener oclBtnNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Next button pressed!");
                playNextSong();
            }
        };
        btnNext.setOnClickListener(oclBtnNext);


        Button btnStop = (Button) findViewById(R.id.btn_stop);
        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Stop button pressed!");
                stopSong();
            }
        };
        btnStop.setOnClickListener(oclBtnStop);
    }


    void togglePlayPause() {
        Log.d("MainActivity", "togglePlayPause");
        boolean isPlaying = mediaPlayer.togglePlayPause();

        if (isPlaying) {
            Log.d("Button", "Song is unpaused");
            tvStatus.setText("Playing");
        } else {
            Log.d("Button", "Song is paused");
            tvStatus.setText("Paused");
        }
    }

    void stopSong() {
        Log.d("MainActivity", "stopSong");
        mediaPlayer.stopSong();
        tvStatus.setText("Stopped");
    }

    void playNextSong() {
        Log.d("MainActivity", "playNextSong");
        String songTitle = mediaPlayer.playNextSong();
        tvSongTitle.setText(songTitle);
        tvStatus.setText("Playing");
    }

    void playPreviousSong() {
        Log.d("MainActivity", "playPreviousSong");
        String songTitle = mediaPlayer.playPreviousSong();
        tvSongTitle.setText(songTitle);
        tvStatus.setText("Playing");
    }
}
