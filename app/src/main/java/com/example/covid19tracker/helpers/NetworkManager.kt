package com.example.covid19tracker.helpers

import com.example.covid19tracker.data.constants.Urls
import com.example.covid19tracker.data.services.GetCasesService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/** Static methods to get concrete implemented objects of service classes
 * Can be optimised better to be thread safe
 * Can be made thread safe either via Dagger's @Singleton annotation or via using Bill Hugh's Singleton Implementation*/

class NetworkManager {

    /**Methods are made static so that they are loaded only once and are alive for the whole of the application lifecycle*/

    companion object {
        private val retrofit =
            Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                    Urls.BASE_URL
                )
                .build()

        fun getCasesService(): GetCasesService {
            return retrofit.create(GetCasesService::class.java)
        }
    }
}