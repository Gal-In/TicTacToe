package com.example.tictactoe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class BoardCellFragment : Fragment() {
    var text : String = ""
    var onClickListener: OnCellClickListener? = null

    interface OnCellClickListener {
        fun onCellClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCellClickListener) {
            onClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_board_cell, container, false)
        val button : Button = view.findViewById(R.id.cell_button)

        button.setOnClickListener {
            if(text == "") {
                text = getPlayerState((activity as MainActivity).currentPlayer)
                button.text = text
                onClickListener?.onCellClicked()
            }
        }

        return view
    }

    fun restartGameCell() {
        val button : Button? = view?.findViewById(R.id.cell_button)
        text = ""
        button?.text = text
    }
}