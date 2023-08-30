package com.aid098.carrepair.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aid098.carrepair.R

class MainFragment : Fragment() {

    private var currentMileage: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val textView : TextView = view.findViewById(R.id.message2)

        val mileage = arguments?.getInt("mileage")

        textView.text = getString(R.string.mileage_main) + " $mileage"

        return view
    }

    fun updateMileage(mileage: Int) {
        currentMileage = mileage
        val textView: TextView = requireView().findViewById(R.id.message2)
        textView.text = getString(R.string.mileage_main) + " $mileage"
    }



}
