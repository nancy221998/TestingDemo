package com.example.demoapp.other

sealed class DataLoadState {
    object Start : DataLoadState()
    object Loading : DataLoadState()
    data class Success(val data: Any) : DataLoadState()
    data class Failed(val msg: String) : DataLoadState()
}