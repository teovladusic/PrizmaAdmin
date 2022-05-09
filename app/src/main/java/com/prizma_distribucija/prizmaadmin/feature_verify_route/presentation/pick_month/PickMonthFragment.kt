package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_month

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.util.Constants
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.databinding.FragmentPickMonthBinding
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Folder
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickMonthFragment : Fragment(R.layout.fragment_pick_month),
    FoldersAdapter.FolderClickListener {

    private var _binding: FragmentPickMonthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PickMonthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPickMonthBinding.bind(requireView())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getEmployeeWithUnseenRoutesStatus.collectLatest { status ->
                    when (status) {
                        is Resource.Error -> {
                            handleGetEmployeeWithUnseenRoutesByIdError(
                                status.message ?: "Unknown message appeared"
                            )
                        }

                        is Resource.Loading -> {
                            handleGetEmployeeWithUnseenRoutesByIdLoading()
                        }

                        is Resource.Success -> {
                            handleGetEmployeeWithUnseenRoutesByIdSuccess(status.data!!)
                        }
                    }
                }
            }
        }
    }

    private fun handleGetEmployeeWithUnseenRoutesByIdError(message: String) {
        binding.getEmployeeProgressBar.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleGetEmployeeWithUnseenRoutesByIdLoading() {
        binding.getEmployeeProgressBar.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun handleGetEmployeeWithUnseenRoutesByIdSuccess(employeeWithUnseenRoutes: EmployeeWithUnseenRoutes) {
        binding.getEmployeeProgressBar.visibility = View.GONE
        binding.tvEmployee.text =
            "${employeeWithUnseenRoutes.employee.name} ${employeeWithUnseenRoutes.employee.lastName}"
        showMonths(Constants.months, employeeWithUnseenRoutes)
    }

    private fun showMonths(
        months: List<String>,
        employeeWithUnseenRoutes: EmployeeWithUnseenRoutes
    ) {

        val folders = months.mapIndexed { index, month ->
            val routesOnMonth =
                employeeWithUnseenRoutes.unseenRoutes.filter { it.month == index + 1 }
            return@mapIndexed Folder(month, routesOnMonth.isEmpty())
        }
        val monthsAdapter = FoldersAdapter(folders, this, R.drawable.ic_folder)
        binding.recViewMonths.apply {
            adapter = monthsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onFolderClick(position: Int) {
        val month = position + 1
        val action = PickMonthFragmentDirections.actionPickMonthFragmentToPickRouteFragment(
            viewModel.employeeWithUnseenRoutes!!,
            month
        )
        findNavController().navigate(action)
    }
}