package com.rickandmortylocations.presentation.audio

import java.awt.Toolkit

actual class AudioManager actual constructor() {
    actual fun playSelectSound() {
        Toolkit.getDefaultToolkit().beep()
    }
}

