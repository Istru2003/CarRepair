package com.aid098.carrepair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainFragment : Fragment() {

    private lateinit var mileageViewModel: MileageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)

        val textViewMileage = rootView.findViewById<TextView>(R.id.textViewMileage)

        mileageViewModel = ViewModelProvider(requireActivity()).get(MileageViewModel::class.java)

        mileageViewModel.mileage.observe(viewLifecycleOwner) { mileage ->
            textViewMileage.text = getString(R.string.mileage_hint, mileage)
        }

        return rootView
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}


