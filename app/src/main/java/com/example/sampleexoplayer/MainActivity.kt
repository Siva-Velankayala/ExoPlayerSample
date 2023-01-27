package com.example.sampleexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playwhenReady = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this).build() //creating an instance of exoplayer
        player?.playWhenReady = true
        val samplePlayer = findViewById<StyledPlayerView>(R.id.sample_player)
        samplePlayer.player = player // attaching player to the view

        //In ExoPlayer every piece of media is represented by a MediaItem.
        // To play a piece of media you need to build a corresponding MediaItem, add it to the player.
        val mediaItem =
            MediaItem.fromUri("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")

        player?.setMediaItem(mediaItem)

        /* ExoPlayer supports playlists directly,
        so it’s possible to prepare the player with multiple media items to be played one after the other:
        MediaItem firstItem = MediaItem.fromUri(firstVideoUri);
        MediaItem secondItem = MediaItem.fromUri(secondVideoUri);

        player.addMediaItem(firstItem);
        player.addMediaItem(secondItem);
        player.prepare();
        player.play();
        */

        player?.seekTo(playbackPosition)
        player?.prepare()
    }

    //Our player can hog a lot of resources including memory, CPU, network connections and hardware codecs
    //It's important that you release those resources for other apps to use when you're not using them,
    // such as when your app is put into the background.
    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            playwhenReady = it.playWhenReady
            it.release()
        }
        player = null
    }

    //Android API level 24 and higher supports multiple windows.
    // As your app can be visible, but not active in split window mode,
    // you need to initialize the player in onStart.
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    // Android API level 23 and lower requires you to wait as long as possible until you grab resources,
    // so you wait until onResume before initializing the player.
    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24) {
            initPlayer()
        }
    }

    //With API Level 23 and lower, there is no guarantee of onStop being called,
    // so you have to release the player as early as possible in onPause
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    // With API Level 24 and higher (which brought multi- and split-window mode), onStop is guaranteed to be called.
    // In the paused state, your activity is still visible, so you wait to release the player until onStop.
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }
}