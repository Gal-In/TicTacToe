package com.example.tictactoe

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.gridlayout.widget.GridLayout
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

enum class PlayerState(val playerName:String) {
    X("X"),
    O("O")
}

fun getPlayerState(player: PlayerState): String {
    return player.playerName
}

class MainActivity : AppCompatActivity() {
    var currentPlayer : PlayerState = PlayerState.X

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button: Button = findViewById(R.id.reset_button)
        button.visibility = View.GONE
        initBoardCells()
    }

    private fun initBoardCells () {
        val gridLayout : GridLayout = findViewById(R.id.board_grid_layout)

        for(row in 1..3) {
            for(col in 1..3) {
                val currFragment = BoardCellFragment()

                currFragment.onClickListener = object : BoardCellFragment.OnCellClickListener {
                    override fun onCellClicked() {
                        if(checkIsEndOfGame()) showResetButton()
                        else switchPlayers()
                    }
                }

                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(gridLayout.id, currFragment, "board-cell-fragment-${row}-${col}")
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

    }

    private fun showResetButton() {
        val button: Button = findViewById(R.id.reset_button)
        button.visibility = View.VISIBLE

        button.setOnClickListener {
                restartGame()
        }
    }

    private fun checkIsEndOfGame(): Boolean {
        // Check rows
        for (row in 1..3) {
            val first = getCellSymbol(row, 1)
            if (first != "" && first == getCellSymbol(row, 2) && first == getCellSymbol(row, 3)) {
                val textView : TextView = findViewById(R.id.message_text_view)
                textView.text = "${currentPlayer} won"
                return true
            }
        }

        // Check columns
        for (col in 1..3) {
            val first = getCellSymbol(1, col)
            if (first != "" && first == getCellSymbol(2, col) && first == getCellSymbol(3, col)) {
                val textView : TextView = findViewById(R.id.message_text_view)
                textView.text = "${currentPlayer} won"
                return true
            }
        }

        // Check diagonals
        val center = getCellSymbol(2, 2)
        if (center != "" && (
                    (center == getCellSymbol(1, 1) && center == getCellSymbol(3, 3)) ||
                            (center == getCellSymbol(1, 3) && center == getCellSymbol(3, 1))
                    )) {
            val textView : TextView = findViewById(R.id.message_text_view)
            textView.text = "${currentPlayer} won"
            return true
        }

        // Check for draw
        var isDraw = true
        for (row in 1..3) {
            for (col in 1..3) {
                if (getCellSymbol(row, col) == "") {
                    isDraw = false // At least one empty cell remains
                    break
                }
            }
            if (!isDraw) break
        }

        if (isDraw) {
            val textView : TextView = findViewById(R.id.message_text_view)
            textView.text = "Its a tie"
            return true
        }

        // Game is not over
        return false
    }

    private fun getCellSymbol(row: Int, col: Int): String {
        val cellFragment = supportFragmentManager.findFragmentByTag("board-cell-fragment-${row}-${col}") as BoardCellFragment
        return cellFragment.text
    }

    private fun switchPlayers() {
        currentPlayer = when(currentPlayer) {
            PlayerState.X -> PlayerState.O
            PlayerState.O -> PlayerState.X
        }

        val textView : TextView = findViewById(R.id.message_text_view)
        textView.text = "It's ${currentPlayer.playerName} turn!"
    }

    private fun restartGame() {
        for(row in 1..3) {
            for(col in 1..3) {
                val boardCellFragment : BoardCellFragment = supportFragmentManager.findFragmentByTag("board-cell-fragment-${row}-${col}") as BoardCellFragment
                boardCellFragment.restartGameCell()
            }
        }

        currentPlayer = PlayerState.X
        val textView : TextView = findViewById(R.id.message_text_view)
        textView.text = "It's ${currentPlayer.playerName} turn!"
        val button: Button = findViewById(R.id.reset_button)
        button.visibility = View.GONE
    }
}