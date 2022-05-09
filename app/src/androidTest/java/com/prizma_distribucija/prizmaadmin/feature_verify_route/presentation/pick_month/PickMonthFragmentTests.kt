package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_month

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.di.launchFragmentInHiltContainer
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickMonthRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@ExperimentalCoroutinesApi
class PickMonthFragmentTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun onCreate_shouldShowMonthsInRecyclerView() {
        lateinit var recView: RecyclerView
        val bundle = Bundle()
        bundle.putString("userId", "id")
        launchFragmentInHiltContainer<PickMonthFragment>(fragmentArgs = bundle) {
            recView = this.requireActivity().findViewById(R.id.rec_view_months)
        }

        assert(recView.adapter != null)
        assert(recView.adapter!!.itemCount == 12)
    }

    @Test
    fun onGetEmployeeByIdLoading_shouldShowProgressBar() {
        val bundle = Bundle()
        bundle.putString("userId", "id")
        PickMonthRepositoryFakeImpl.delayTime = 1000L
        PickMonthRepositoryFakeImpl.isSuccessful = false
        launchFragmentInHiltContainer<PickMonthFragment>(fragmentArgs = bundle) { }

        onView(withId(R.id.get_employee_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun onGetEmployeeByIdSuccess_shouldHideProgressBarAndSetUi() {
        val bundle = Bundle()
        bundle.putString("userId", "id")
        PickMonthRepositoryFakeImpl.delayTime = 0L
        PickMonthRepositoryFakeImpl.isSuccessful = true
        launchFragmentInHiltContainer<PickMonthFragment>(fragmentArgs = bundle) { }

        onView(withId(R.id.get_employee_progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_employee)).check(matches(withText("name lastName")))
    }

    @Test
    fun onGetEmployeeByIdError_shouldHideProgressBarAndShowSnackbar() {
        val bundle = Bundle()
        bundle.putString("userId", "id")
        PickMonthRepositoryFakeImpl.delayTime = 0L
        PickMonthRepositoryFakeImpl.isSuccessful = false
        launchFragmentInHiltContainer<PickMonthFragment>(fragmentArgs = bundle) { }

        onView(withId(R.id.get_employee_progress_bar)).check(matches(not(isDisplayed())))
        onView(withText("error message")).check(matches(isDisplayed()))
    }

    @Test
    fun onFolderClick_shouldNavigateToPickRouteFragment() {
        val bundle = Bundle()
        bundle.putString("userId", "id")
        PickMonthRepositoryFakeImpl.delayTime = 0L
        PickMonthRepositoryFakeImpl.isSuccessful = true
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<PickMonthFragment>(fragmentArgs = bundle) {
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.action_pickEmployeeFragment_to_pickMonthFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rec_view_months))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<FoldersAdapter.FoldersViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        assert(navController.currentDestination?.id == R.id.pickRouteFragment)
    }
}