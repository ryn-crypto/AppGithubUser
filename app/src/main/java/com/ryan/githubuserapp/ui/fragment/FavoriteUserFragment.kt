package com.ryan.githubuserapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryan.githubuserapp.data.viewmodel.FUViewModelFactory
import com.ryan.githubuserapp.databinding.FragmentUserListBinding
import com.ryan.githubuserapp.data.viewmodel.FavoriteUserViewModel
import com.ryan.githubuserapp.data.viewmodel.UserListViewModel
import com.ryan.githubuserapp.ui.activity.FavoriteUserListAdapter

class FavoriteUserFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var favoriteUserListAdapter: FavoriteUserListAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteUserViewModel = ViewModelProvider(this, FUViewModelFactory.getInstance(requireActivity().application))[FavoriteUserViewModel::class.java]

        favoriteUserListAdapter = FavoriteUserListAdapter(favoriteUserViewModel)
        binding.recyclerView.adapter = favoriteUserListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        showLoading(true)

        favoriteUserViewModel.favoriteUsers.observe(viewLifecycleOwner) { favoriteUsers ->
            showLoading(false)
            favoriteUserListAdapter.submitList(favoriteUsers)
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
        fun newInstance(): FavoriteUserFragment {
            return FavoriteUserFragment()
        }
    }
}
