package com.example.demoapp.retrofit

import com.example.demoapp.model.Media
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL = "https://acharyaprashant.org/api/"
    }

    @GET("v2/content/misc/media-coverages?limit=100")
    suspend fun getPosts(): Response<Media>

}