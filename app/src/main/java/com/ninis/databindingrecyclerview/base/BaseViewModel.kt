package com.ninis.databindingrecyclerview.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Throwable>()

    @CallSuper
    override fun onCleared() {
        disposable.clear()
    }
}