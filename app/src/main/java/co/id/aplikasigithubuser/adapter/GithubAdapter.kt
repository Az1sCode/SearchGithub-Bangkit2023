package co.id.aplikasigithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.databinding.UserGithubBinding
import co.id.aplikasigithubuser.ui.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GithubAdapter : ListAdapter<Itemsitem, GithubAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: UserGithubBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: Itemsitem){
            binding.tvNameUser.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).apply(RequestOptions.circleCropTransform()).into(binding.userImage)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("username", user.login)
                context.startActivity(intent)
                Toast.makeText(context, "${user.login}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Itemsitem>() {
            override fun areItemsTheSame(oldItem: Itemsitem, newItem: Itemsitem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Itemsitem, newItem: Itemsitem): Boolean {
                return oldItem == newItem
            }
        }
    }

}