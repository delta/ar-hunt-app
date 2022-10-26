package edu.nitt.delta.orientation22.di.api

import retrofit2.http.POST

interface ApiInterface {

    @POST(ApiRoutes.LOGIN)
    suspend fun login(): Any
}
