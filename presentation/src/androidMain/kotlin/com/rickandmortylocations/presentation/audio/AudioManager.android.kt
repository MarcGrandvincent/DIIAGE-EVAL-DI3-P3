package com.rickandmortylocations.presentation.audio

import android.media.AudioManager as AndroidAudioManager
import android.media.ToneGenerator

actual class AudioManager actual constructor() {
    actual fun playSelectSound() {
        val tone = ToneGenerator(AndroidAudioManager.STREAM_NOTIFICATION, 80)
        tone.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
        tone.release()
    }
}

