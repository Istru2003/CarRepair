package com.aid098.carrepair.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aid098.carrepair.MainActivity
import com.aid098.carrepair.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val activity = requireActivity() as MainActivity

        val textViewCarName: TextView = view.findViewById(R.id.message1)
        val textViewMileage: TextView = view.findViewById(R.id.message2)

        // Access carName and mileage properties through getter methods
        textViewCarName.text = activity.getCarName()
        textViewMileage.text = activity.getMileage().toString()

        return view
    }
}
