package com.aid098.carrepair

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class IntroChoiceFragment : Fragment() {

    // Объявление переменных
    private lateinit var editTextViewMileage: EditText
    private lateinit var editTextViewName: EditText
    private lateinit var mileageViewModel: MileageViewModel
    private var actionBarTitleListener: ActionBarTitleListener? = null


    // Метод, вызываемый при создании представления фрагмента
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Надувание макета (layout) для фрагмента
        val rootView = inflater.inflate(R.layout.intro_choice_page, container, false)

        // Привязка переменной к элементу EditText в макете
        editTextViewMileage = rootView.findViewById<EditText>(R.id.mileage)
        editTextViewName = rootView.findViewById<EditText>(R.id.carName)

        // Создание и получение экземпляра ViewModel из активности-хоста фрагмента
        mileageViewModel = ViewModelProvider(requireActivity()).get(MileageViewModel::class.java)

        actionBarTitleListener?.setActionBarTitle(getName())



        return rootView
    }

    // Метод для получения значения пробега из EditText
    fun getMileage(): Int {
        val mileageText = editTextViewMileage.text.toString()
        return if (mileageText.isNotEmpty()) {
            mileageText.toInt()
        } else {
            0
        }
    }

    fun setMileage(mileage: Int) {
        editTextViewMileage.setText(mileage.toString())
    }

    fun getName(): String {
        val Carname = editTextViewName.text.toString()
        return if (Carname.isNotEmpty()) {
            Carname
        } else {
            "Home"
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarTitleListener) {
            actionBarTitleListener = context
        }
    }


    override fun onResume() {
        super.onResume()

        val carName = getName()
        actionBarTitleListener?.setActionBarTitle(carName)
    }

    // Статический метод, используемый для создания экземпляра фрагмента
    companion object {
        fun newInstance(): IntroChoiceFragment {
            return IntroChoiceFragment()
        }
    }

    interface ActionBarTitleListener {
        fun setActionBarTitle(title: String)
        fun getCarName(): String // Add this function to get the car name
    }


}



