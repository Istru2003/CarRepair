package com.aid098.carrepair.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aid098.carrepair.Communicator
import com.aid098.carrepair.R

class IntroInfoFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carName = "Default Car"
        val mileage = 0
        communicator.passData(carName, mileage)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator) {
            communicator = context
        } else {
            throw IllegalArgumentException("Context must implement Communicator interface")
        }
    }

    fun setCallback(callback: Communicator) {
        this.callback = callback
    }
}
