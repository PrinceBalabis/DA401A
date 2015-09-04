package princetronics.assignment4;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Prince on 04-Sep-15.
 */
public class MyMediaPlayer {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int amountOfSongs = 3; // Amount of songs in app
    private int playlistID[] = new int[amountOfSongs];
    private String songTitles[] = {"Swedens National Anthem", "Denmarks National Anthem", "Finlands National Anthem"};
    private int playlistPosition = 0; // Start position in playlist
    private Context context;
    private boolean isPlaying = true;

    public MyMediaPlayer(Context context) {
        this.context = context;

        customPlaylistInit(); // Initialize playlist
        playSongInPlaylist(playlistPosition); // Start playing
    }

    void customPlaylistInit() {
        // Load songs
        playlistID[0] = R.raw.swedens_national_anthem;
        playlistID[1] = R.raw.denmarks_national_anthem;
        playlistID[2] = R.raw.finlands_national_anthem;
    }

    void playSongInPlaylist(int position) {
        playlistPosition = position;
        mediaPlayer = MediaPlayer.create(context, playlistID[playlistPosition]); // Load song
        mediaPlayer.start(); // Start playing song
    }

    // returns current song title
    String getCurrentSongTitle() {
        return songTitles[playlistPosition];
    }

    // returns false if paused, true if currently playing
    boolean togglePlayPause() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            return false;
        } else {
            mediaPlayer.start();
            isPlaying = true;
            return true;
        }
    }

    public void onPreparedListener(MediaPlayer mediaplayer) {
        // We now have buffered enough to be able to play
        mediaPlayer.start();
    }

    // Returns song title
    String playNextSong() {
        mediaPlayer.stop(); // Stop current song

        // Go to next song or reset position to first song if position is at last song
        if (playlistPosition < 2) {
            playlistPosition++;
        } else {
            playlistPosition = 0;
        }

        playSongInPlaylist(playlistPosition); // Start next song
        return songTitles[playlistPosition];
    }

    String playPreviousSong() {
        mediaPlayer.stop(); // Stop current song

        // Go to next song or reset position to first song if position is at last song
        if (playlistPosition > 0) {
            playlistPosition--;
        } else {
            playlistPosition = 2;
        }

        playSongInPlaylist(playlistPosition); // Start next song
        return songTitles[playlistPosition];
    }

    void stopSong() {
        mediaPlayer.pause(); // Pause song
        mediaPlayer.seekTo(0); // Rewind to start of song
        isPlaying = false;
    }
}
