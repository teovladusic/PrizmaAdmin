package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.di.launchFragmentInHiltContainer
import com.prizma_distribucija.prizmaadmin.core.util.atPositionOnView
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Folder
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_employee.PickEmployeeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class FoldersAdapterTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun showCorrectFolderName() {
        val folder = Folder("name name", true)

        val folderClickListener = object : FoldersAdapter.FolderClickListener {
            override fun onFolderClick(position: Int) {
                return
            }
        }

        val foldersAdapter =
            FoldersAdapter(listOf(folder), folderClickListener, R.drawable.ic_folder)

        launchFragmentInHiltContainer<PickEmployeeFragment> {
            val recView = requireActivity().findViewById<RecyclerView>(R.id.rec_view_employees)
            recView.adapter = foldersAdapter
            recView.layoutManager = LinearLayoutManager(requireContext())
        }

        onView(withId(R.id.rec_view_employees)).check(matches(hasDescendant(withText("name name"))))
    }

    @Test
    fun correctlyShowNewRouteIndicator() {
        val folder = Folder("name name", false)

        val folderClickListener = object : FoldersAdapter.FolderClickListener {
            override fun onFolderClick(position: Int) {
                return
            }
        }

        val foldersAdapter =
            FoldersAdapter(listOf(folder), folderClickListener, R.drawable.ic_folder)

        launchFragmentInHiltContainer<PickEmployeeFragment> {
            val recView = requireActivity().findViewById<RecyclerView>(R.id.rec_view_employees)
            recView.adapter = foldersAdapter
            recView.layoutManager = LinearLayoutManager(requireContext())
        }

        onView(withId(R.id.rec_view_employees)).check(
            matches(
                atPositionOnView(
                    0,
                    isDisplayed(),
                    R.id.new_route_indicator
                )
            )
        )
    }

    @Test
    fun correctlyHideNewRouteIndicator() {
        val folder = Folder("name name", true)

        val folderClickListener = object : FoldersAdapter.FolderClickListener {
            override fun onFolderClick(position: Int) {
                return
            }
        }

        val foldersAdapter =
            FoldersAdapter(listOf(folder), folderClickListener, R.drawable.ic_folder)

        launchFragmentInHiltContainer<PickEmployeeFragment> {
            val recView = requireActivity().findViewById<RecyclerView>(R.id.rec_view_employees)
            recView.adapter = foldersAdapter
            recView.layoutManager = LinearLayoutManager(requireContext())
        }

        onView(withId(R.id.rec_view_employees)).check(
            matches(
                atPositionOnView(
                    0,
                    not(isDisplayed()),
                    R.id.new_route_indicator
                )
            )
        )
    }
}