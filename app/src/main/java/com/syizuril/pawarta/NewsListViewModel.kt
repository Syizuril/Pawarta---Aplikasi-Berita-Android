package com.syizuril.pawarta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syizuril.pawarta.model.ApiStatus
import com.syizuril.pawarta.model.NewsModel
import com.syizuril.pawarta.model.Source
import com.syizuril.pawarta.network.NewsApi
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _listResult = MutableLiveData<NewsModel>()
    val listResult: LiveData<NewsModel>
        get() = _listResult

    fun getListNews(source: String){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _listResult.value = NewsApi.retrofitService.getEverythingByDomain(source)
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun getListNewsByCategory(category: String){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _listResult.value = NewsApi.retrofitService.getByCategory("id",category)
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}