package org.example.kobableclone.ui.game

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.example.kobableclone.R
import org.example.kobableclone.game.base.BaseGame
import org.example.kobableclone.game.level1.Game1

class GameActivity : AppCompatActivity() {
    private lateinit var game: BaseGame
    private lateinit var dragContainer: ViewGroup
    private lateinit var commandPalette: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        
        initializeGame()
        setupDragAndDrop()
        setupCommandPalette()
    }
    
    private fun initializeGame() {
        game = when (intent.getIntExtra("gameId", 1)) {
            1 -> Game1()
            2 -> Game2()
            3 -> Game3()
            else -> Game1()
        }
        game.initialize()
    }
    
    private fun setupDragAndDrop() {
        // Implement drag and drop functionality for commands
    }
    
    private fun setupCommandPalette() {
        // Setup available commands in a RecyclerView
    }
}

