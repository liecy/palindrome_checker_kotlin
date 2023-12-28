package com.rahmi.palindromechecker.di

import com.rahmi.palindromechecker.data.Repository
import com.rahmi.palindromechecker.data.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(
            apiService
        )
    }
}