package princetronics.assignment4;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Prince on 04-Sep-15.
 */
public class MyMediaPlayer {

    private MediaPlayer mediaPlayers[] = new MediaPlayer[3];
    private String songTitles[] = {"Swedens National Anthem", "Denmarks National Anthem", "Finlands National Anthem"};
    private int playlistPosition = 0;
    private Context context;


    public MyMediaPlayer(Context context) {
        this.context = context;

        // Load song
        mediaPlayers[0] = MediaPlayer.create(context, R.raw.swedens_national_anthem);
        mediaPlayers[1] = MediaPlayer.create(context, R.raw.denmarks_national_anthem);
        mediaPlayers[2] = MediaPlayer.create(context, R.raw.finlands_national_anthem);

        // Enable looping for all songs
        for (int i = 0; i < 3; i++) {
            mediaPlayers[i].setLooping(true); // Enable music looping for each song
        }

        mediaPlayers[playlistPosition].start(); // Start playing first song
    }


    // returns current song title
    String getCurrentSongTitle() {
        return songTitles[playlistPosition];
    }

    // returns false if paused, true if currently playing
    boolean togglePlayPause(){
        if(mediaPlayers[playlistPosition].isPlaying()){
            mediaPlayers[playlistPosition].pause();
            return false;
        } else {
            mediaPlayers[playlistPosition].start();
            return true;
        }
    }
}
