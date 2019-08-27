package com.ninis.databindingrecyclerview.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ninis.databindingrecyclerview.base.BaseViewModel
import com.ninis.databindingrecyclerview.network.ApiService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class MainViewModel(private val service: ApiService): BaseViewModel() {
    val mainItems = MutableLiveData<MutableList<AlbumItemModel>>()

    val gson = Gson()

    fun getData() {
        service.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponseBody> {
                override fun onSuccess(t: ResponseBody) {
                    val packagedArray
                            = gson.fromJson(t.string(), Array<AlbumItemModel>::class.java).toMutableList()

                    mainItems.value = packagedArray
                    loading.value = false
                }

                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                    loading.value = true
                }

                override fun onError(e: Throwable) {
                    error.value = e
                }

            })
    }
}