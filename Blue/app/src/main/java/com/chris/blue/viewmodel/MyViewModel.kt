package com.chris.blue.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {


    val number : MutableLiveData<Int> = MutableLiveData()


//    val number : MutableLiveData<Int> by lazy { MutableLiveData<>() }


    fun modifyNumber(aNumber : Int){
        number.value =  number.value?.plus(aNumber)
    }


}
