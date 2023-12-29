package co.id.aplikasigithubuser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.aplikasigithubuser.data.entity.UserEntity
import co.id.aplikasigithubuser.data.room.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = userRepository.isLoading

    fun getFavoritedUser() = userRepository.getUserFavorited()

    fun userSaveAndDelete(user: UserEntity, isFavorited: Boolean) {
        viewModelScope.launch {
            if (isFavorited) {
                userRepository.deleteFavorite(user, false)
            } else {
                userRepository.insertFavorite(user, true)
            }
        }
    }
}