package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_route

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.databinding.FragmentPickRouteBinding
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Folder
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickRouteFragment : Fragment(R.layout.fragment_pick_route),
    FoldersAdapter.FolderClickListener {

    private var _binding: FragmentPickRouteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PickRouteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPickRouteBinding.bind(view)

        val monthName = Constants.months[viewModel.month - 1]
        setTitle(
            "${viewModel.employeeWithUnseenRoutes.employee.name} ${viewModel.employeeWithUnseenRoutes.employee.lastName} - ",
            monthName
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getRoutesStatus.collect { status ->
                    when (status) {
                        is Resource.Error -> handleGetRoutesError(
                            status.message ?: "Unknown error appeared"
                        )

                        is Resource.Loading -> handleGetRoutesLoading()

                        is Resource.Success -> handleGetRoutesSuccess(status.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun handleGetRoutesError(message: String) {
        binding.getRoutesProgressBar.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleGetRoutesSuccess(data: List<Route>) {
        binding.getRoutesProgressBar.visibility = View.GONE
        showRoutes(data)
    }

    private fun showRoutes(routes: List<Route>) {
        val folders =
            routes.map {
                Folder(
                    "${it.day}.${it.month}.${it.year}, ${it.timeStarted} - ${it.timeFinished}",
                    it.seen
                )
            }
        binding.recViewRoutes.apply {
            adapter = FoldersAdapter(folders, this@PickRouteFragment, R.drawable.ic_route)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleGetRoutesLoading() {
        binding.getRoutesProgressBar.visibility = View.VISIBLE
    }

    private fun setTitle(employeeName: String, monthName: String) {
        binding.tvEmployeeName.text = employeeName
        binding.tvMonthName.text = monthName
    }

    override fun onFolderClick(position: Int) {
        val route = viewModel.routes[position]
        val action = PickRouteFragmentDirections.actionPickRouteFragmentToCheckRouteFragment(
            viewModel.employeeWithUnseenRoutes.employee,
            route
        )

        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}