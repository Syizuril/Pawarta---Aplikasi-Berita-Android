package com.syizuril.pawarta

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syizuril.pawarta.model.ApiStatus
import com.syizuril.pawarta.model.NewsModel
import com.syizuril.pawarta.network.NewsApi
import kotlinx.coroutines.launch
import kotlin.math.log

class TopHeadlineViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _topHeadlineResult = MutableLiveData<NewsModel>()
    val topHeadlineResult: LiveData<NewsModel>
        get() = _topHeadlineResult

    init {
        getTopHeadline()
    }

    fun getTopHeadline(){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _topHeadlineResult.value = NewsApi.retrofitService.getTopHeadline("id")
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}