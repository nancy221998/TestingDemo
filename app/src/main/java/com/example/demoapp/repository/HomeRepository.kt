package com.example.demoapp.repository

import com.example.demoapp.model.Media
import com.example.demoapp.retrofit.ApiService
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun execute(): Response<Media> {
        return apiService.getPosts()
    }
}