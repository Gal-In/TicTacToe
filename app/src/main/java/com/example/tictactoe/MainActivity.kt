package com.example.tictactoe

import android.os.Bundle
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

    private fun checkIsEndOfGame() {
        // TODO: implement this function, both for empty board, and a winner
        // supportFragmentManager.findFragmentByTag("board-cell-fragment-${row}-${col}")

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
    }
}