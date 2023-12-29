package co.id.aplikasigithubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.aplikasigithubuser.data.response.DetailResponse
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> get() = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLloading: LiveData<Boolean> = _isLoading

    private val _userData = MutableLiveData<List<Itemsitem>>()
    val userData: LiveData<List<Itemsitem>> get() = _userData

    fun findDetailUser(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun userFollower(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<Itemsitem>> {
            override fun onResponse(
                call: Call<List<Itemsitem>>,
                response: Response<List<Itemsitem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userData.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<Itemsitem>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Follower gagal", "mengambil $username")
            }

        })
    }

    fun userFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<Itemsitem>> {
            override fun onResponse(
                call: Call<List<Itemsitem>>,
                response: Response<List<Itemsitem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userData.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<Itemsitem>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Follower gagal", "mengambil $username")
            }

        })
    }

    fun userRepos(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRepos(username)
        client.enqueue(object : Callback<List<Itemsitem>> {
            override fun onResponse(
                call: Call<List<Itemsitem>>,
                response: Response<List<Itemsitem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userData.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<Itemsitem>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Follower gagal", "mengambil $username")
            }

        })
    }

}