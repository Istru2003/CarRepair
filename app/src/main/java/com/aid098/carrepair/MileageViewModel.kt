package com.aid098.carrepair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MileageViewModel : ViewModel() {
    private val _mileage = MutableLiveData<Int>()
    val mileage: LiveData<Int>
        get() = _mileage

    init {
        _mileage.value = 0
    }

    fun updateMileage(newMileage: Int) {
        _mileage.value = newMileage
    }
}


