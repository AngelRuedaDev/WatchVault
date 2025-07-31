package com.angelruedadev.watchvault.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelruedadev.watchvault.data.local.entity.TvShowEntity
import com.angelruedadev.watchvault.data.repository.LocalRepository
import com.angelruedadev.watchvault.data.repository.TvShowRepository
import com.angelruedadev.watchvault.domain.model.TvShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(private val tvShowRepository: TvShowRepository, private val localRepository: LocalRepository) : ViewModel(){
    private val _tvShow = MutableStateFlow<TvShowDetail?>(null)
    val tvShow: StateFlow<TvShowDetail?> = _tvShow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _tvShowEntity = MutableStateFlow<TvShowEntity?>(null)
    val tvShowEntity : StateFlow<TvShowEntity?> = _tvShowEntity

    fun fetchDetail(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = tvShowRepository.getTvShowDetails(id)
                _tvShow.value = response

                fetchInfoFromLocal(id)

            }catch(e: Exception){
                _error.value = e.localizedMessage ?: "Unknown error"
            }finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchInfoFromLocal(id: Int) {
        _tvShowEntity.value = localRepository.getTvShowById(id)
    }

    fun toggleLike() {
        viewModelScope.launch {
            val tvShow = _tvShow.value ?: return@launch

            var currentEntity = _tvShowEntity.value

            if (currentEntity == null) {
                currentEntity = TvShowEntity(
                    id = tvShow.id,
                    title = tvShow.name,
                    photoPath = tvShow.posterPath,
                    isLiked = true
                )
                localRepository.insertTvShow(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(isLiked = !currentEntity.isLiked)
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(tvShow.id)
        }
    }

    fun toggleWatchList() {
        viewModelScope.launch {
            val tvShow = _tvShow.value ?: return@launch

            var currentEntity = _tvShowEntity.value

            if (currentEntity == null) {
                currentEntity = TvShowEntity(
                    id = tvShow.id,
                    title = tvShow.name,
                    photoPath = tvShow.posterPath,
                    isInWatchList = true
                )
                localRepository.insertTvShow(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(isInWatchList = !currentEntity.isInWatchList)
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(tvShow.id)
        }
    }

    private suspend fun checkIfUpdateOrDelete(updatedEntity: TvShowEntity) {
        val shouldDelete = !updatedEntity.isLiked && !updatedEntity.isInWatchList && updatedEntity.userRating == 0

        if (shouldDelete) {
            localRepository.deleteTvShow(updatedEntity)
        } else {
            localRepository.insertTvShow(updatedEntity)
        }
    }

    fun rateTvShow(rating: Float) {
        viewModelScope.launch {
            val tvShow = _tvShow.value ?: return@launch

            var currentEntity = _tvShowEntity.value

            if (currentEntity == null) {
                currentEntity = TvShowEntity(
                    id = tvShow.id,
                    title = tvShow.name,
                    photoPath = tvShow.posterPath,
                    userRating = (rating * 2).toInt()
                )
                localRepository.insertTvShow(currentEntity)
            } else {
                val updatedEntity = currentEntity.copy(userRating = (rating * 2).toInt())
                checkIfUpdateOrDelete(updatedEntity)
            }

            // Refresh the state from the local database
            fetchInfoFromLocal(tvShow.id)
        }
    }


}