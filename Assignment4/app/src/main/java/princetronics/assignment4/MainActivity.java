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

    private MediaPlayer mediaPlayers[] = new MediaPlayer[3];
    private String songNames[] = {"Swedens National Anthem","Denmarks National Anthem","Finlands National Anthem"};
    private int playlistPosition = 0;

    private TextView tvSongName, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewsInit();

        mediaPlayerInit();
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



    void mediaPlayerInit() {
        // Load songs
        mediaPlayers[0] = MediaPlayer.create(this, R.raw.swedens_national_anthem);
        mediaPlayers[1] = MediaPlayer.create(this, R.raw.denmarks_national_anthem);
        mediaPlayers[2] = MediaPlayer.create(this, R.raw.finlands_national_anthem);

        tvSongName.setText(songNames[playlistPosition]); // draw song title in app

        for(int i = 0; i< 3; i++){
            mediaPlayers[i].setLooping(true); // Enable music looping for each song
        }

        tvStatus.setText("Playing"); // Update media player status

        mediaPlayers[0].start(); // Start playing first song
    }

    void viewsInit() {
        tvSongName = (TextView) findViewById(R.id.tv_songname);
        tvStatus = (TextView) findViewById(R.id.tv_status);

        Button btnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        View.OnClickListener oclBtnPlayPause = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Play/pause button pressed!");

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
                Log.d("Button", "NExt button pressed!");

            }
        };
        btnNext.setOnClickListener(oclBtnNext);


        Button btnStop = (Button) findViewById(R.id.btn_next);
        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Stop button pressed!");

            }
        };
        btnStop.setOnClickListener(oclBtnStop);
    }
}
