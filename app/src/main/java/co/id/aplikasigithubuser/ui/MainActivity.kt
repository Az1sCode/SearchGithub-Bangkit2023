package co.id.aplikasigithubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.app.AlertDialog.Builder
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.aplikasigithubuser.R
import co.id.aplikasigithubuser.adapter.GithubAdapter
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.databinding.ActivityMainBinding
import co.id.aplikasigithubuser.model.MainViewModel
import co.id.aplikasigithubuser.model.ViewModelFactory

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvDataGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvDataGithub.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.userData.observe(this) { userGithubs ->
            setUserData(userGithubs)
        }

        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_fav -> {
                    val intent = Intent(this@MainActivity ,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_theme -> {
                    themeDialog()
                    true
                }
                else -> false
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                val searchBar = searchView.text
                searchView.hide()
                mainViewModel.findUser(searchBar.toString())
                false
            }
        }

        mainViewModel.getThemeSetting().observe(this) {isNightMode ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }

    private fun themeDialog() {
        val mode = arrayOf("Dark Mode", "Light Mode")

        val builder = Builder(this)
        builder.setTitle("Select Theme")
        builder.setItems(mode) {_, which ->
            mainViewModel.saveThemeSetting(which == 0)
            Toast.makeText(this, "Apply theme", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun setUserData(userGithubs: List<Itemsitem>?) {
        val adapter = GithubAdapter()
        adapter.submitList(userGithubs)
        binding.rvDataGithub.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}