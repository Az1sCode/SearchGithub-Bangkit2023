package co.id.aplikasigithubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import co.id.aplikasigithubuser.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

//    @Query("SELECT * FROM user ORDER BY username")
//    fun getUserProfile(query: String?): LiveData<List<UserEntity>>

    @Update
    fun update(user: UserEntity)

    @Query("SELECT * from user WHERE favorite = 1")
    fun getFavoritedUser(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username and favorite = 1)")
    fun isFavorite(username: String?): Boolean

    @Delete
    fun delete(user: UserEntity)

}