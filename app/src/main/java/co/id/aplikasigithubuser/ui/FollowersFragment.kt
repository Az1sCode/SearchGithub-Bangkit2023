package co.id.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.aplikasigithubuser.R
import co.id.aplikasigithubuser.adapter.GithubAdapter
import co.id.aplikasigithubuser.data.response.Itemsitem
import co.id.aplikasigithubuser.model.DetailViewModel

class FollowersFragment : Fragment() {

    private lateinit var rvFollower: RecyclerView
    private lateinit var tvLabel: TextView
    private lateinit var progressbar: ProgressBar

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower = view.findViewById(R.id.rv_follower)
        progressbar = view.findViewById(R.id.progressBar)


        val layoutManager = LinearLayoutManager(requireActivity())
        rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        rvFollower.addItemDecoration(itemDecoration)

        val detailViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java
            )

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
            username = it.getString(ARG_USERNAME,username)
        }

        if (position % 3 == 1) {
            detailViewModel.userFollower(username)
            detailViewModel.userData.observe(viewLifecycleOwner) {userData ->
                setFollower(userData)
            }
        } else if (position % 2 == 1){
            detailViewModel.userFollowing(username)
            detailViewModel.userData.observe(viewLifecycleOwner) {userData ->
                setFollower(userData)
            }
        } else {
            detailViewModel.userRepos(username)
            detailViewModel.userData.observe(viewLifecycleOwner) {userData ->
                setFollower(userData)
            }
        }

    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "app_name"
    }

    private fun setFollower(usersGithub: List<Itemsitem>) {
        val adapter = GithubAdapter()
        adapter.submitList(usersGithub)
        rvFollower.adapter = adapter
    }

}