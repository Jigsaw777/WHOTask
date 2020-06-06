package com.example.covid19tracker.domain.usecases

import Repository
import com.example.covid19tracker.domain.entities.CaseInfoClass
import com.example.covid19tracker.domain.requests.GetCasesRequest
import io.reactivex.Single

class GetCasesInfoUseCase(private val repo: Repository) {
    fun fetchResults(getCasesRequest: GetCasesRequest): Single<CaseInfoClass> {
        return repo.getCasesInformation(getCasesRequest)
    }
}