package princetronics.assignment4;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayers[] = new MediaPlayer[3];
    private String songNames[] = {"Swedens National Anthem","Denmarks National Anthem","Finlands National Anthem"};
    private int playlistPosition = 0;

    private TextView tv_songName;

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

    void viewsInit() {
        tv_songName = (TextView) findViewById(R.id.tv_songname);
    }

    void mediaPlayerInit() {
        // Load songs
        mediaPlayers[0] = MediaPlayer.create(this, R.raw.swedens_national_anthem);
        mediaPlayers[1] = MediaPlayer.create(this, R.raw.denmarks_national_anthem);
        mediaPlayers[2] = MediaPlayer.create(this, R.raw.finlands_national_anthem);

        tv_songName.setText(songNames[playlistPosition]); // draw song title in app

        for(int i = 0; i< 3; i++){
            mediaPlayers[i].setLooping(true); // Enable music looping for each song
        }

        mediaPlayers[0].start(); // Play audio
    }

}
