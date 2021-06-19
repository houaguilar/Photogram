package com.example.firebase.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.firebase.R
import com.example.firebase.core.Resource
import com.example.firebase.data.remote.home.HomeScreenDataSource
import com.example.firebase.databinding.FragmentHomeScreenBinding
import com.example.firebase.domain.home.HomeScreenRepoImpl
import com.example.firebase.presentation.HomeScreenViewModel
import com.example.firebase.presentation.HomeScreenViewModelFactory
import com.example.firebase.ui.home.adapter.HomeScreenAdapter
import com.example.firebase.utils.hide
import com.example.firebase.utils.show

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.show()
                        return@Observer
                    } else {
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}