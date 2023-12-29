package co.id.aplikasigithubuser.data.retrofit

import co.id.aplikasigithubuser.data.response.DetailResponse
import co.id.aplikasigithubuser.data.response.UserResponse
import co.id.aplikasigithubuser.data.response.Itemsitem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String?
    ):Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ):Call<List<Itemsitem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ):Call<List<Itemsitem>>

    @GET("users/{username}/repos")
    fun getRepos(
        @Path("username") username: String?
    ):Call<List<Itemsitem>>
}