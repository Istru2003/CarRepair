package com.aid098.carrepair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MileageViewModel : ViewModel() {
    // Создание приватного изменяемого LiveData объекта для хранения текущего пробега.
    private val _mileage = MutableLiveData<Int>()

    // Публичное неизменяемое LiveData свойство, через которое можно получить доступ к пробегу.
    val mileage: LiveData<Int>
        get() = _mileage

    // Инициализация начального значения пробега в конструкторе ViewModel.
    init {
        _mileage.value = 0
    }

    // Метод для обновления значения пробега с новым переданным значением.
    fun updateMileage(newMileage: Int) {
        _mileage.value = newMileage
    }
}



