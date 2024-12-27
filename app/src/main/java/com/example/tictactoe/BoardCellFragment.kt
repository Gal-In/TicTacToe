package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val ROW_PARAM = "rowParam"
private val COL_PARAM = "colParam"


class BoardCellFragment : Fragment() {
    private var row: Int = 0
    private var col: Int = 0
    private var text : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            row = it.getInt(ROW_PARAM)
            col = it.getInt(COL_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("sometag", "created view")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_board_cell, container, false)



        val someOtherView = view.findViewById<Button>(R.id.cell_button)

        someOtherView.setOnClickListener {
            Log.d("sometag", row.toString())
            Log.d("sometag", col.toString())

        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BoardCellFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(row: Int, col: Int) =
            BoardCellFragment().apply {
                arguments = Bundle().apply {
                    Log.d("sometag", row.toString())
                    Log.d("sometag", col.toString())

                    putInt(ROW_PARAM, row)
                    putInt(COL_PARAM, col)
                }
            }
    }
}