package co.id.aplikasigithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.aplikasigithubuser.R
import co.id.aplikasigithubuser.adapter.FavoriteAdapter
import co.id.aplikasigithubuser.databinding.ActivityFavoriteBinding
import co.id.aplikasigithubuser.model.FavoriteViewModel
import co.id.aplikasigithubuser.model.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = layoutManager

        viewModel.getFavoritedUser().observe(this, Observer { username ->
            binding.progressbar.visibility = View.GONE
            adapter.userListUpdate(username)
        })
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}