package com.aid098.carrepair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainFragment : Fragment() {

    // Создаем позднюю инициализацию для ViewModel, которая будет использоваться в данном фрагменте
    private lateinit var mileageViewModel: MileageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Надуваем макет фрагмента из XML-ресурса
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)

        // Находим TextView в макете по его идентификатору
        val textViewMileage = rootView.findViewById<TextView>(R.id.textViewMileage)

        // Инициализируем ViewModel, связанную с активностью, и получаем доступ к наблюдаемым данным
        mileageViewModel = ViewModelProvider(requireActivity()).get(MileageViewModel::class.java)

        // Настраиваем наблюдение за изменениями данных "mileage" в ViewModel
        mileageViewModel.mileage.observe(viewLifecycleOwner) { mileage ->
            // Обновляем текст в TextView, используя ресурс строки и значение пробега
            textViewMileage.text = getString(R.string.mileage_hint, mileage)
        }

        return rootView
    }

    companion object {
        // Создаем статический метод для создания нового экземпляра MainFragment
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}



