package com.chris.blue.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.chris.blue.bean.PhotoItem
import com.chris.blue.bean.Pixabay
import com.chris.blue.demo.KotlinTest
import com.chris.blue.utils.VolleySingleton
import com.google.gson.Gson

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "GalleryViewModel"

    val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive: LiveData<List<PhotoItem>>
        get() = _photoListLive

    private val keyWords = arrayOf("cat", "dog", "phone", "beauty", "car", "computer", "flower", "animal")

    fun fetchData() {
        Log.d(TAG,"fetchData()")
        val stringRequest = StringRequest(
                Request.Method.GET,
                getUrl(),
                Response.Listener {
                    _photoListLive.value = Gson().fromJson(it,Pixabay::class.java).hits.toList()
                },
                Response.ErrorListener {
                    Log.e(TAG,"Error : ${it.message}")
                }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    fun getUrl(): String {
        return "https://pixabay.com/api/?key=17721584-dbaaa5819b20af6517d618e01&q=${keyWords.random()}&per_page=100"
    }
}