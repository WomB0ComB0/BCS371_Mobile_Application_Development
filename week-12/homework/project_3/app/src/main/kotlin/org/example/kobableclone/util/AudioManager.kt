package org.example.kobableclone.util

import android.content.Context
import android.media.MediaPlayer
import android.media.AudioAttributes
import org.example.kobableclone.R
import javax.naming.Context

class AudioManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var isSoundEnabled = true

    init {
        setupAudioAttributes()
    }

    private fun setupAudioAttributes() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
        }
    }

    fun playSound(soundResourceId: Int) {
        if (!isSoundEnabled) return

        try {
            mediaPlayer?.apply {
                reset()
                setDataSource(context.resources.openRawResourceFd(soundResourceId))
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleSound() {
        isSoundEnabled = !isSoundEnabled
        if (!isSoundEnabled) {
            stopSound()
        }
    }

    fun stopSound() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            reset()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        const val SOUND_GAME_START = R.raw.game_start
        const val SOUND_SUCCESS = R.raw.success
        const val SOUND_FAILURE = R.raw.failure
        const val SOUND_COLLECT = R.raw.collect_item
        const val SOUND_LEVEL_COMPLETE = R.raw.level_complete
        const val SOUND_POWER_UP = R.raw.power_up
        const val SOUND_TELEPORT = R.raw.teleport
    }
}

