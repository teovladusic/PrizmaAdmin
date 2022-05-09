package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_employee

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
import com.prizma_distribucija.prizmaadmin.core.util.Resource
import com.prizma_distribucija.prizmaadmin.databinding.FragmentPickEmployeeBinding
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Folder
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickEmployeeFragment : Fragment(R.layout.fragment_pick_employee),
    FoldersAdapter.FolderClickListener {

    private var _binding: FragmentPickEmployeeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PickEmployeeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPickEmployeeBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getEmployeesWithUnseenRoutesStatus.collectLatest { status ->
                    when (status) {
                        is Resource.Loading -> {
                            handleGetEmployeesWithUnseenRoutesLoading()
                        }
                        is Resource.Success -> {
                            handleGetEmployeesWithUnseenRoutesSuccess(status.data ?: emptyList())
                        }

                        is Resource.Error -> {
                            handleGetEmployeesWithUnseenRoutesError(status.message ?: "Unknown error appeared")
                        }
                    }
                }
            }
        }
    }

    private fun handleGetEmployeesWithUnseenRoutesLoading() {
        setProgressBarVisibility(View.VISIBLE)
    }

    private fun handleGetEmployeesWithUnseenRoutesSuccess(employees: List<EmployeeWithUnseenRoutes>) {
        setProgressBarVisibility(View.GONE)
        showEmployees(employees)
    }

    private fun handleGetEmployeesWithUnseenRoutesError(message: String) {
        setProgressBarVisibility(View.GONE)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setProgressBarVisibility(visibility: Int) {
        binding.getUsersProgressBar.visibility = visibility
    }

    override fun onFolderClick(position: Int) {
        val userId = viewModel.employeesWithUnseenRoutes[position].employee.userId
        val action =
            PickEmployeeFragmentDirections.actionPickEmployeeFragmentToPickMonthFragment(userId)
        findNavController().navigate(action)
    }

    private fun showEmployees(employeesWithUnseenRoutes: List<EmployeeWithUnseenRoutes>) {
        val folders = employeesWithUnseenRoutes.map { Folder("${it.employee.name} ${it.employee.lastName}", it.unseenRoutes.isEmpty()) }
        val adapter = FoldersAdapter(folders, this, R.drawable.ic_folder)
        binding.recViewEmployees.adapter = adapter
        binding.recViewEmployees.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}