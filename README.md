1. [MediaPlayback article](https://developer.android.com/guide/topics/media/mediaplayer.html)

2. [Media player documentation](https://developer.android.com/reference/android/media/MediaPlayer.html) that shows the full state machine diagram

3. More details about [Anonymous classes](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)

4. Async callbacks

5. Cleaning up media Resources(free up resources when no longer needed so that other apps on the evice can use sa well)
	- Determine when you no longer need Media resources
	- How can you free up the resources
	- Documentation for the MediaPlayer [release()](http://developer.android.com/reference/android/media/MediaPlayer.html?utm_source=udacity&utm_medium=course&utm_campaign=android_basics#release()) method
	- [MediaPlayer State diagram](http://developer.android.com/reference/android/media/MediaPlayer.html?utm_source=udacity&utm_medium=course&utm_campaign=android_basics#StateDiagram)

6. AudioFocus(Android uses audio focus to manage audio playback on the device)

	- Understanding Audio Focus
		- [Part 1](https://medium.com/androiddevelopers/audio-focus-1-6b32689e4380)
		- [Part 2](https://medium.com/androiddevelopers/audio-focus-2-42244043863a)
		- [Part 3](https://medium.com/androiddevelopers/audio-focus-3-cdc09da9c122)

	- [How to properly handle audio interruptions](https://medium.com/google-developers/how-to-properly-handle-audio-interruptions-3a13540d18fa#.jkibca8ml)

	- [Managing Audio Focus](https://developer.android.com/guide/topics/media-apps/audio-focus)

	- AudioManager class in Android framework allows us to:
		- Request audio focus
		- Abandon audio focus
		- Register a Listener to get notified of audio focus state change

	- For a list of possible streamType values, see the constants that start with the name “STREAM_” in the [AudioManager](https://developer.android.com/reference/android/media/AudioManager) class.

	- For a list of possible durationHint values, see the constants in the parameter description of the [requestAudioFocus](https://developer.android.com/reference/android/media/AudioManager.html?utm_source=udacity&utm_medium=course&utm_campaign=android_basics#requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener,%20int,%20int))) method.	







