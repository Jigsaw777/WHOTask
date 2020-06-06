package com.example.covid19tracker.data.services

import com.example.covid19tracker.data.constants.Urls
import com.example.covid19tracker.domain.entities.CaseInfoClass
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GetCasesService {
    @GET(Urls.CASES_INFO_URL)
    fun getCasesInfo(@QueryMap data: Map<String, String>): Single<CaseInfoClass>
}