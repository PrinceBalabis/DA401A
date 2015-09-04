package princetronics.assignment4;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Prince on 04-Sep-15.
 */
public class MyMediaPlayer {

    final private String TAG = "MyMediaPlayer";

    private int amountOfSongs = 3; // Amount of songs in app
    private MediaPlayer mp = new MediaPlayer();
    private int playlistID[] = new int[amountOfSongs];
    private String songTitles[] = {"Swedens National Anthem", "Denmarks National Anthem", "Finlands National Anthem"};
    private int playlistPosition = 0;
    private Context context;


    public MyMediaPlayer(Context context) {
        this.context = context;

        customPlaylistInit();

        // This listener starts the song whenever song has finished preparing
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "Prepare finished!");
                mp.start();
            }
        });

        mp.create(this.context, playlistID[playlistPosition]); // Load first songs
        //mp.setLooping(true); // Enable music looping for each song


    }

    void customPlaylistInit() {
        // Load songs
        playlistID[0] = R.raw.swedens_national_anthem;
        playlistID[1] = R.raw.denmarks_national_anthem;
        playlistID[2] = R.raw.finlands_national_anthem;
    }

    // returns current song title
    String getCurrentSongTitle() {
        return songTitles[playlistPosition];
    }

    // returns false if paused, true if currently playing
    boolean togglePlayPause() {
        if (mp.isPlaying()) {
            mp.pause();
            return false;
        } else {
            mp.start();
            return true;
        }
    }

    void playNextSong() {
        mp.reset(); // Stop current song

        // Go to next song or reset position to first song if position is at last song
        if (playlistPosition < 2) {
            playlistPosition++;
        } else {
            playlistPosition = 0;
        }

        mp.start(); // Start next song
    }

    void playPreviousSong() {

    }

    void stopSong() {

    }
}
