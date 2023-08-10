package com.aid098.carrepair.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.aid098.carrepair.R

class IntroInfoFragment : Fragment() {

    lateinit var editTextCarName: EditText
    lateinit var editTextMileage: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_info, container, false)
        editTextCarName = view.findViewById(R.id.CarName)
        editTextMileage = view.findViewById(R.id.Mileage)
        return view
    }



}