package co.id.aplikasigithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.id.aplikasigithubuser.data.entity.UserEntity
import co.id.aplikasigithubuser.databinding.UserGithubBinding
import co.id.aplikasigithubuser.ui.DetailActivity
import co.id.aplikasigithubuser.utils.UserFavUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var favList = emptyList<UserEntity>()

    fun userListUpdate(userList: List<UserEntity>) {
        val diff = DiffUtil.calculateDiff(UserFavUtils(favList, userList))
        this.favList = userList
        diff.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(val binding: UserGithubBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.tvNameUser.text = user.username
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.userImage)

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("username", user.username)
                intent.putExtra("avatarUrl", user.avatarUrl)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserGithubBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = favList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFav = favList[position]
        holder.bind(userFav)
    }
}