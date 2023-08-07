package com.aid098.carrepair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class IntroChoiceFragment : Fragment() {

    private lateinit var editTextViewMileage: EditText
    private lateinit var mileageViewModel: MileageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.intro_choice_page, container,false)

        editTextViewMileage = rootView.findViewById<EditText>(R.id.mileage)

        mileageViewModel = ViewModelProvider(requireActivity()).get(MileageViewModel::class.java)

        return rootView
    }


    fun getMileage(): Int {
        val mileageText = editTextViewMileage.text.toString()
        return if (mileageText.isNotEmpty()) {
            mileageText.toInt()
        } else {
            0
        }
    }

    companion object {
        fun newInstance(): IntroChoiceFragment {
            return IntroChoiceFragment()
        }
    }
}


