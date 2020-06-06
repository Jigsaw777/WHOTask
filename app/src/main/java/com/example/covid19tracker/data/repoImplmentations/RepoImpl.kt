package com.example.covid19tracker.data.repoImplmentations

import Repository
import com.example.covid19tracker.domain.entities.CaseInfoClass
import com.example.covid19tracker.domain.requests.GetCasesRequest
import com.example.covid19tracker.helpers.NetworkManager
import io.reactivex.Single

class RepoImpl : Repository {

    /** A naive way of making sure,we use only one instance of GetCasesService. This part can be modified/optimised
     * via Dagger @Inject. We can inject the singleton object in RepoImpl's constructor instead of making a static object with class dependency.
     *
     * This static object can be created lazily the first time its used. This makes sure, memory is not allocated unnecessarily and be used
     * as and when required.*/

    companion object {
        val getCasesService by lazy { NetworkManager.getCasesService() }
    }

    override fun getCasesInformation(getCasesRequest: GetCasesRequest): Single<CaseInfoClass> {
        return getCasesService.getCasesInfo(getCasesRequest.getParams())
    }
}