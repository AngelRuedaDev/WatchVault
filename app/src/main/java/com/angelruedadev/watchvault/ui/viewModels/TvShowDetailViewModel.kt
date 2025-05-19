package com.angelruedadev.watchvault.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.repository.TvShowRepository
import com.angelruedadev.watchvault.domain.model.TvShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(private val tvShowRepository: TvShowRepository) : ViewModel(){
    private val _tvShow = MutableStateFlow<TvShowDetail?>(null)
    val tvShow: StateFlow<TvShowDetail?> = _tvShow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchDetail(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = tvShowRepository.getTvShowDetails(id)
                _tvShow.value = response
            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }
}