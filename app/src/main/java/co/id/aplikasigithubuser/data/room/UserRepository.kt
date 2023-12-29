package co.id.aplikasigithubuser.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import co.id.aplikasigithubuser.data.entity.UserEntity
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.data.response.UserResponse
import co.id.aplikasigithubuser.data.retrofit.ApiService
import co.id.aplikasigithubuser.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors,
    private val preferences: SettingPreferences
) {
    private val _userData = MutableLiveData<List<Itemsitem>?>()
    val userData: LiveData<List<Itemsitem>?> get() = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val result = MediatorLiveData<Result<List<Itemsitem>>>()

    fun findUser(query: String){
        _isLoading.value = true
        val client = apiService.getListUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.value = response.body()?.items

                } else {
                    Log.e("Logku", "respons no success: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getUserFavorited(): LiveData<List<UserEntity>> {
        return userDao.getFavoritedUser()
    }

    fun insertFavorite(user: UserEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = favoriteState
            userDao.insert(user)
        }
    }

    fun deleteFavorite(user: UserEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = favoriteState
            userDao.delete(user)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: UserDao,
            appExecutors: AppExecutors,
            preferences: SettingPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao, appExecutors, preferences)
            }.also { instance = it }
    }
}