package com.example.covid19tracker.views.viewmodels

import Repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covid19tracker.data.repoImplmentations.RepoImpl
import com.example.covid19tracker.domain.entities.Attribute
import com.example.covid19tracker.domain.requests.GetCasesRequest
import com.example.covid19tracker.domain.usecases.GetCasesInfoUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(){

    /** naive way of making a singleton object of repo Implementation. Can optimise this better with Dagger*/
    companion object {
        private val repository: Repository = RepoImpl()
        val getCasesInfoUseCase = GetCasesInfoUseCase(repository)
    }

    /** disposables are used to avoid crashes and if there is any ongoing network event, they dispose
     *  the network event safely. Disposables respect the activity/fragment's lifecycle */
    private val compositeDisposable = CompositeDisposable()

    private val getAttributes = MutableLiveData<List<Attribute>>()

    val attributesLiveData : LiveData<List<Attribute>>
            get() = getAttributes


     fun getData(code:String) {
        val getCasesRequest = GetCasesRequest(code)
        getCasesInfoUseCase.fetchResults(getCasesRequest).subscribeOn(Schedulers.io()).subscribe({
            Log.d("tag","it obj : $it")
            if(it != null) {
                val feature = it.features.maxBy { it.attributes.totalConfirmedCases }
                /** Dummy attribute to differentiate between first card */
                val dummy = Attribute(totalConfirmedCases = -1L, totalDeathCases = -1L, objectId = -1)
                val attributes = mutableListOf<Attribute>()
                attributes.add(dummy)
                attributes.add(feature?.attributes?:dummy)
                getAttributes.postValue(attributes)
            }
            else{
                Log.d("tag","null is coming")
            }
        }, {
            print(it.localizedMessage)
        }).let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}