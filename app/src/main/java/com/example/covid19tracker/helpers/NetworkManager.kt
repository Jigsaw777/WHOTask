package com.example.covid19tracker.helpers

import com.example.covid19tracker.data.constants.AppConstants
import com.example.covid19tracker.data.services.GetCasesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/** Static methods to get concrete implemented objects of service classes
 * Can be optimised better to be thread safe
 * Can be made thread safe either via Dagger's @Singleton annotation or via using Bill Hugh's Singleton Implementation*/

class NetworkManager {

    /**Methods are made static so that they are loaded only once and are alive for the whole of the application lifecycle*/

    companion object {

        /** Not potentially safe to use a logger in production environment as it can cause unwanted leaks.
         * However, using logging interceptor in this project as needed for debug */
        private fun httpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient().newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(loggingInterceptor).build()
        }

        private val retrofit =
            Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                    AppConstants.BASE_URL
                )
                .client(httpClient())
                .build()

        fun getCasesService(): GetCasesService {
            return retrofit.create(GetCasesService::class.java)
        }
    }
}