package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.databinding.FragmentPickUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickUserFragment : Fragment(R.layout.fragment_pick_user) {

    private var _binding: FragmentPickUserBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PickUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPickUserBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUserStatus.collectLatest { status ->
                    when (status) {
                        is Resource.Loading -> {
                            handleLoading()
                        }
                        is Resource.Success -> {
                            handleGetUsersSuccess()
                        }

                        is Resource.Error -> {
                            handleGetUsersError()
                        }
                    }
                }
            }
        }
    }

    private fun handleLoading() {
        setProgressBarVisibility(View.VISIBLE)
    }

    private fun handleGetUsersSuccess() {
        setProgressBarVisibility(View.GONE)
    }

    private fun handleGetUsersError() {
        setProgressBarVisibility(View.GONE)
    }

    private fun setProgressBarVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}