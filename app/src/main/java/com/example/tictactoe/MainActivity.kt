package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.gridlayout.widget.GridLayout

class MainActivity : AppCompatActivity() {
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
                Log.d("sometag", "sometext")
                val currFragment = BoardCellFragment.newInstance(row, col)

                val params = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(row) // Set the row position
                    columnSpec = GridLayout.spec(col) // Set the column position
                    width = 0 // Let GridLayout handle the width
                    height = 0 // Let GridLayout handle the height
                }

                currFragment.view?.layoutParams = params

                val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(gridLayout.id, currFragment) // Use replace instead of add
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

    }
}