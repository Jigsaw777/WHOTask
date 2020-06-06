package com.example.covid19tracker.views.viewmodels

import Repository
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel(){

    /** disposables are used to avoid crashes and if there is any ongoing network event, they dispose
     *  the network event safely. Disposables respect the activity/fragment's lifecycle */
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}