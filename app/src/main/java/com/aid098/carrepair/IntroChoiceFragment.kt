package com.aid098.carrepair

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
    private lateinit var mileageViewModel: MileageViewModel

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

        // Создание и получение экземпляра ViewModel из активности-хоста фрагмента
        mileageViewModel = ViewModelProvider(requireActivity()).get(MileageViewModel::class.java)

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

    // Статический метод, используемый для создания экземпляра фрагмента
    companion object {
        fun newInstance(): IntroChoiceFragment {
            return IntroChoiceFragment()
        }
    }
}



