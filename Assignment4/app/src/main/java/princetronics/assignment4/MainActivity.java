package princetronics.assignment4;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        mediaPlayer = new MyMediaPlayer(this); // Start media player
        viewsInit(); // initialize views
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

    void viewsInit() {
        tvSongTitle = (TextView) findViewById(R.id.tv_songtitle);
        tvStatus = (TextView) findViewById(R.id.tv_status);

        tvSongTitle.setText(mediaPlayer.getCurrentSongTitle()); // draw song title in app
        tvStatus.setText("Playing"); // Update media player status

        final Button btnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        btnPlayPause.setText("Pause");
        View.OnClickListener oclBtnPlayPause = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Play/pause button pressed!");
                boolean isPlaying = mediaPlayer.togglePlayPause();

                if (isPlaying){
                    Log.d("Button", "Song is unpaused");
                    tvStatus.setText("Playing");
                    btnPlayPause.setText("Pause");
                }else {
                    Log.d("Button", "Song is paused");
                    tvStatus.setText("Paused");
                    btnPlayPause.setText("Play");
                }
            }
        };
        btnPlayPause.setOnClickListener(oclBtnPlayPause);

        Button btnPrevious = (Button) findViewById(R.id.btn_previous);
        View.OnClickListener oclBtnPrevious = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Previous button pressed!");

            }
        };
        btnPrevious.setOnClickListener(oclBtnPrevious);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        View.OnClickListener oclBtnNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Next button pressed!");
                mediaPlayer.playNextSong();
                tvSongTitle.setText(mediaPlayer.getCurrentSongTitle()); // draw song title in app
            }
        };
        btnNext.setOnClickListener(oclBtnNext);


        Button btnStop = (Button) findViewById(R.id.btn_stop);
        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Stop button pressed!");

            }
        };
        btnStop.setOnClickListener(oclBtnStop);
    }
}
