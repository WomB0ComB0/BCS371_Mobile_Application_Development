package org.example

import android.app.Application
import org.example.kobableclone.data.local.DatabaseHelper
import org.example.kobableclone.util.AudioManager

class App : Application() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var audioManager: AudioManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeDependencies()
    }

    private fun initializeDependencies() {
        databaseHelper = DatabaseHelper.getInstance(this)
        audioManager = AudioManager(this)
    }

    companion object {
        private lateinit var instance: App
        
        fun getInstance(): App = instance
        
        fun getDatabase(): DatabaseHelper = instance.databaseHelper
        
        fun getAudioManager(): AudioManager = instance.audioManager
    }
}
