package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.gridlayout.widget.GridLayout

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

        initBoardCells()
    }

    private fun initBoardCells () {
        val gridLayout : GridLayout = findViewById(R.id.board_grid_layout)

        for(row in 1..3) {
            for(col in 1..3) {
                val currFragment = BoardCellFragment()

                currFragment.onClickListener = object : BoardCellFragment.OnCellClickListener {
                    override fun onCellClicked() {
                        switchPlayers()
                        checkIsEndOfGame()
                    }
                }

                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(gridLayout.id, currFragment, "board-cell-fragment-${row}-${col}")
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

    }

    private fun checkIsEndOfGame(): Boolean {
        // TODO: implement this function, both for empty board, and a winner
        // supportFragmentManager.findFragmentByTag("board-cell-fragment-${row}-${col}")
        // Check rows
        for (row in 0..2) {
            val first = getCellSymbol(row, 0)
            if (first != " " && first == getCellSymbol(row, 1) && first == getCellSymbol(row, 2)) {
                println("Player $first wins!")
                return true
            }
        }

        // Check columns
        for (col in 0..2) {
            val first = getCellSymbol(0, col)
            if (first != " " && first == getCellSymbol(1, col) && first == getCellSymbol(2, col)) {
                println("Player $first wins!")
                return true
            }
        }

        // Check diagonals
        val center = getCellSymbol(1, 1)
        if (center != " " && (
                    (center == getCellSymbol(0, 0) && center == getCellSymbol(2, 2)) ||
                            (center == getCellSymbol(0, 2) && center == getCellSymbol(2, 0))
                    )) {
            println("Player $center wins!")
            return true
        }

        // Check for draw
        var isDraw = true
        for (row in 0..2) {
            for (col in 0..2) {
                if (getCellSymbol(row, col) == " ") {
                    isDraw = false // At least one empty cell remains
                    break
                }
            }
            if (!isDraw) break
        }

        if (isDraw) {
            println("It's a draw!")
            return true
        }

        // Game is not over
        return false
    }

    private fun getCellSymbol(row: Int, col: Int): String {
//        val tag = "board-cell-fragment-$row-$col"
//        supportFragmentManager.findFragmentByTag(tag) as? BoardCellFragment
        val cellFragment = supportFragmentManager.findFragmentByTag("board-cell-fragment-${row}-${col}")
        return cellFragment.toString()
    }

    private fun switchPlayers() {
        currentPlayer = when(currentPlayer) {
            PlayerState.X -> PlayerState.O
            PlayerState.O -> PlayerState.X
        }

        val textView : TextView = findViewById(R.id.message_text_view)
        textView.text = "It's ${currentPlayer.playerName} turn!"
    }
}