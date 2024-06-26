package com.example.demoapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.other.DataLoadState
import com.example.demoapp.other.isInternetAvailable
import com.example.demoapp.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository,
                                        val savedStateHandle: SavedStateHandle,
                                        @ApplicationContext private val context: Context
) : ViewModel(){

    private val _dataLoadStateFlow = MutableStateFlow<DataLoadState>(DataLoadState.Start)
    val dataLoadStateFlow: StateFlow<DataLoadState> get() = _dataLoadStateFlow

    init {
        fetchData()
    }

    fun refresh(){
        fetchData()
    }

    fun fetchData() {
        _dataLoadStateFlow.value = DataLoadState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            if(isInternetAvailable(context)){
                val result = repository.execute()
                if (result.isSuccessful) {
                    result.body()?.let {

                        Log.e("TAG", "fetchData: ${it.toString()}", )
                        _dataLoadStateFlow.value = DataLoadState.Success(it)
                    } ?: kotlin.run {
                        _dataLoadStateFlow.value = DataLoadState.Failed("Failed parse response body")
                    }
                } else {
                    _dataLoadStateFlow.value = DataLoadState.Failed("Api failed to return response")
                }
            }else{
              //  Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show()
            }

        }
    }

}