package com.aid098.carrepair.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aid098.carrepair.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val textView1 : TextView = view.findViewById(R.id.message1)
        val textView2 : TextView = view.findViewById(R.id.message2)

        val carName = arguments?.getString("carName")
        val mileage = arguments?.getString("mileage")

        textView1.text = "Car name: $carName"
        textView2.text = "Your mileage: $mileage"
        return view
    }



}
