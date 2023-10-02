package com.ryan.githubuserapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryan.githubuserapp.data.viewmodel.FUViewModelFactory
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.databinding.FragmentUserListBinding
import com.ryan.githubuserapp.data.viewmodel.UserListViewModel

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: UserListViewModel
    private var username: String? = null
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)
        type = arguments?.getString(ARG_TYPE)

        val favoriteUserViewModel = ViewModelProvider(this, FUViewModelFactory.getInstance(requireActivity().application))[FavoriteUserViewModel::class.java]
        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        adapter = UserListAdapter(favoriteUserViewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        showLoading(true)

        if (!username.isNullOrEmpty() && !type.isNullOrEmpty()) {
            viewModel.fetchUserList(username!!, type!!)
        }

        viewModel.userList.observe(viewLifecycleOwner) { users ->
            showLoading(false)
            if (users != null) {
                adapter.submitList(users)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun updateSearchQuery(query: String) {
        if (query.isNotBlank()) {
            viewModel.queryUserListByCriteria(query)
        } else {
            viewModel.restoreOriginalUserList()
        }
    }

    companion object {
        private const val ARG_USERNAME = "username"
        private const val ARG_TYPE = "type"

        fun newInstance(username: String, type: String): UserListFragment {
            val fragment = UserListFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

}