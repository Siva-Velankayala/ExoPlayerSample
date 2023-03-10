﻿# ExoPlayerSample
ExoPlayer is an application-level media player for Android which provides an alternative to Android’s MediaPlayer. It can play audio/video both locally and over the Internet. Another reason why ExoPlayer is a popular option is that it supports a lot of features, for example, Dynamic Adaptive Streaming over HTTP (DASH) and SmoothStreaming which aren’t supported by MediaPlayer. It is very customizable and provides a lot of other features and abilities which can be found here.


The instructions listed below have been used to use Exoplayer on Android.

1) Add a gradle dependency for the full library  
```implementation 'com.google.android.exoplayer:exoplayer:2.X.X'```   
As an alternative to the full library, you can depend on only the library modules that you actually need.  
```implementation 'com.google.android.exoplayer:exoplayer-core:2.X.X'```  
```implementation 'com.google.android.exoplayer:exoplayer-dash:2.X.X'```  
```implementation 'com.google.android.exoplayer:exoplayer-ui:2.X.X'```  


2) Add a playerview to xml by making use of Exoplayer’s prebuilt UI-components  
```<com.google.android.exoplayer2.ui.StyledPlayerView```  
   ```android:id="@+id/sample_player"```  
   ```android:layout_width="match_parent"```  
   ```android:layout_height="match_parent"/>```  

    The ExoPlayer library provides a range of pre-built UI components for media playback. These include StyledPlayerView, which encapsulates a StyledPlayerControlView, a SubtitleView, and a Surface onto which video is rendered. A StyledPlayerView can be included in your application’s layout xml.  

    Use of ExoPlayer’s pre-built UI components is optional. For video applications that implement their own UI, the target SurfaceView, TextureView, SurfaceHolder or Surface can be set using ExoPlayer’s setVideoSurfaceView, setVideoTextureView, setVideoSurfaceHolder and setVideoSurface methods respectively. ExoPlayer’s addTextOutput method can be used to receive captions that should be rendered during playback.  




3) Create and Attach the player to player view  
```private var player: ExoPlayer? = null```  

    ```player = ExoPlayer.Builder(this).build()```  
     ```samplePlayer.player = player```  

4) Creating a mediaItem and preparing the player  
  In ExoPlayer every piece of media is represented by a MediaItem. To play a piece of media you need to build a corresponding MediaItem, add it to the player  

    ```val mediaItem = MediaItem.fromUri(videoUri)```  

    ```player?.setMediaItem(mediaItem)```  

    ```player?.prepare()```  

      ExoPlayer supports playlists directly, so it’s possible to prepare the player with multiple media items to be played one after the other:  

    ```// Build the media items.```  
    ```MediaItem firstItem = MediaItem.fromUri(firstVideoUri);```  
    ```MediaItem secondItem = MediaItem.fromUri(secondVideoUri);```  
    ```// Add the media items to be played.```  
    ```player.addMediaItem(firstItem);```  
    ```player.addMediaItem(secondItem);```  
    ```// Prepare the player.```  
    ```player.prepare();```  
    ```// Start the playback.```  
    ```player.play();```  


5) Releasing the player  
   It’s important to release the player when it’s no longer needed, so as to free up limited resources such as video decoders for use by other applications  
    ```private fun releasePlayer() {```  
       ```player?.let {```  
          ```playbackPosition = it.currentPosition```  
           ```playwhenReady = it.playWhenReady```  
          ```it.release()```  
       ```}```  
       ```player = null```  
    ```}```  

      Made use of the activity lifecycle methods to initialize and releasing player  
