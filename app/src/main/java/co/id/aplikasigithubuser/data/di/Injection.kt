package co.id.aplikasigithubuser.data.di

import android.content.Context
import co.id.aplikasigithubuser.data.retrofit.ApiConfig
import co.id.aplikasigithubuser.data.room.SettingPreferences
import co.id.aplikasigithubuser.data.room.UserDatabase
import co.id.aplikasigithubuser.data.room.UserRepository
import co.id.aplikasigithubuser.data.room.dataStore
import co.id.aplikasigithubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        val preferences = SettingPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, dao, appExecutors, preferences)
    }
}