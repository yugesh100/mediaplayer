package com.example.android_mediaplayer_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;


import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button pauseButton;
    AudioManager audioManager;


   private  MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Toast.makeText(MainActivity.this, "I'm done", Toast.LENGTH_SHORT).show();
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.


                mediaPlayer.pause();

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                Log.d(TAG, "AUDIOFOCUS_GAIN");
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                Log.d(TAG, "AUDIOFOCUS_LOSS");
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);

        // TODO (01) create raw res directory and put media files
        // Select File->New->Android Resource Directory->select raw

        releaseMediaPlayer();

        // TODO (02) create the media player object
        //mediaPlayer = MediaPlayer.create(this, R.raw.happy_birthday_ringtone);

        // TODO (03) implement onClickListener for play and pause button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
                requestAudioFocus();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });


        // TODO (04) set onCompletionListener callback to get notified when media player is done playing


        // TODO (05) clean up the media resources
        // 1. Determine when you no longer need Media resources
        // 2. How can you free up the resources
        //Release after the sound file has finished playing
        //also release the MediaPlayer resources before mediaPlayer is initialized to play a different song

        //TODO (07) Audio Focus
        /** Android uses audio focus to manage audio playback on the device. So this means only apps that are holding audio focus
         * should be able to play audio at any given time
         *
         */
        //1. What method do you call to request audio focus from the android system?
        //2. What method do you call to release audio focus?
        //3. What callback do you implement to get notified when audio focus state changes?

        /**
         * 1. Request Audio Focus
         * 2. Create an instance of AudioManager.OnAudioFocusChangeListener and implement the callback method
         * 3. Adapt playback behavior when audio focus state changes
         * 4. Release audioFocus when no longer needed
         */

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    private void requestAudioFocus() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            AudioAttributes audioAttributes =
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build();
            AudioFocusRequest audioFocusRequest =
                    new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                            .setAudioAttributes(audioAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                            .build();

            int focusRequest = audioManager.requestAudioFocus(audioFocusRequest);
            switch (focusRequest) {
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:

                case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                    mediaPlayer = MediaPlayer.create(this, R.raw.happy_birthday_ringtone);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
            }

        }else{

            int focusRequest = audioManager.requestAudioFocus(onAudioFocusChangeListener,

                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            switch (focusRequest) {
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                    // don't start playback
                    break;
                case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                    // actually start playback

                    mediaPlayer = MediaPlayer.create(this, R.raw.happy_birthday_ringtone);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
                    break;
            }
        }
    }

    //TODO (06) MediaPlayer and activity lifecycle
    //If the user leaves the activity, during which activity lifecycle stage should we release resources used by the Media Player?


    @Override
    protected void onStop() {
        super.onStop();
        //mediaPlayer.release();
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
