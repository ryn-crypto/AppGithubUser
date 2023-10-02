package com.ryan.githubuserapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryan.githubuserapp.data.viewmodel.FUViewModelFactory
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.databinding.FragmentUserListBinding
import com.ryan.githubuserapp.data.viewmodel.SearchUserViewModel
import com.ryan.githubuserapp.helper.toUserListResponseList

class SearchUserFragment: Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: SearchUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        val query = arguments?.getString(ARG_QUERY)

        val favoriteUserViewModel = ViewModelProvider(this, FUViewModelFactory.getInstance(requireActivity().application))[FavoriteUserViewModel::class.java]
        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]

        adapter = UserListAdapter(favoriteUserViewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        if (!query.isNullOrEmpty()) {
            viewModel.searchUsers(query)
        }

        viewModel.userList.observe(viewLifecycleOwner) { searchResponseList ->
            showLoading(false)
            if (searchResponseList != null) {
                adapter.submitList(searchResponseList.toUserListResponseList())
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

    companion object {
        private const val ARG_QUERY = "query"

        fun newInstance(query: String): SearchUserFragment {
            val fragment = SearchUserFragment()
            val args = Bundle()
            args.putString(ARG_QUERY, query)
            fragment.arguments = args
            return fragment
        }
    }
}
