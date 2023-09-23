package com.example.osmtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.osmtest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PointInfoBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_point_info, container, false)

        val historyButton = view.findViewById<Button>(R.id.historyButton)
        historyButton.setOnClickListener {
        }

        return view
    }
}