package co.id.aplikasigithubuser.ui

import android.graphics.BlurMaskFilter.Blur
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import co.id.aplikasigithubuser.R
import co.id.aplikasigithubuser.adapter.SectionPagerAdapter
import co.id.aplikasigithubuser.data.entity.UserEntity
import co.id.aplikasigithubuser.data.response.DetailResponse
import co.id.aplikasigithubuser.databinding.ActivityDetailBinding
import co.id.aplikasigithubuser.model.DetailViewModel
import co.id.aplikasigithubuser.model.FavoriteViewModel
import co.id.aplikasigithubuser.model.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jp.wasabeef.glide.transformations.BlurTransformation
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val viewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private var avatarUrl: String? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2,
            R.string.tab_text3
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val username = intent.getStringExtra("username").toString()

        detailViewModel.userDetail.observe(this) {userDetail ->
            Log.d("Logku","${userDetail.login}")
            setUserDetail(userDetail)
            this.avatarUrl = userDetail.avatarUrl.toString()
        }

        detailViewModel.isLloading.observe(this) {
            showLoading(it)
        }

        detailViewModel.findDetailUser(username)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> detailViewModel.userFollower(username)
                    1 -> detailViewModel.userFollowing(username)
                    else -> detailViewModel.userRepos(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModel.getFavoritedUser().observe(this) {favList ->
            val isFavorite = favList.any {
                it.username == username
            }
            setIconFab(isFavorite)

            binding.btnFab.setOnClickListener {
                val userFav = username.let { UserEntity(it, avatarUrl, false) }

                try {
                    if (userFav != null) viewModel.userSaveAndDelete(userFav, favList.any{
                        it.username == username
                    })
                } catch (e: Exception) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }

                if (isFavorite) {
                    Toast.makeText(this, "Delete data", Toast.LENGTH_SHORT).show()
                    setIconFab(isFavorite)
                } else {
                    Toast.makeText(this, "Add data", Toast.LENGTH_SHORT).show()
                    setIconFab(isFavorite)
                }
            }
        }
    }

    private fun setIconFab(isFavorited: Boolean) {
        binding.btnFab.apply {
            if (isFavorited) {
                setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_favorite_24))
            } else {
                setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_favorite_border_24))
            }
        }
    }

    private fun setUserDetail(userDetail: DetailResponse) {
        binding.username.text = userDetail.login
        binding.nameUser.text = userDetail.name
        binding.location.text = userDetail.location
        binding.userFollowers.text = "${userDetail.followers}"
        binding.userFollowing.text = "${userDetail.following}"
        binding.userRepository.text = "${userDetail.publicRepos}"
        Glide.with(this).load(userDetail.avatarUrl).apply(RequestOptions.circleCropTransform()).into(binding.imageUser)
        Glide.with(this).load(userDetail.avatarUrl).apply(RequestOptions.bitmapTransform(BlurTransformation(25,3))).into(binding.bgImageUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}