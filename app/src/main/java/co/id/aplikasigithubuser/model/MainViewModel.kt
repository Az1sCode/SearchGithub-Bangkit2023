package co.id.aplikasigithubuser.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.data.room.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel() {

    val userData: LiveData<List<Itemsitem>?> = userRepository.userData
    val isLoading: LiveData<Boolean> = userRepository.isLoading

    fun findUser(query: String) = userRepository.findUser(query)

    fun getThemeSetting(): LiveData<Boolean> = userRepository.getThemeSettings()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(isDarkModeActive)
        }
    }

}