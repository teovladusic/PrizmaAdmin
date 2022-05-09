package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_employee

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.di.launchFragmentInHiltContainer
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickEmployeeRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class PickEmployeeFragmentTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun handleGetAllEmployeesLoading_shouldShowProgressBar() {
        PickEmployeeRepositoryFakeImpl.isSuccess = false
        PickEmployeeRepositoryFakeImpl.delayTime = 10000L
        launchFragmentInHiltContainer<PickEmployeeFragment> {
        }

        onView(withId(R.id.get_users_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun handleGetAllEmployeesSuccess_shouldHideProgressBar() {
        PickEmployeeRepositoryFakeImpl.isSuccess = true
        PickEmployeeRepositoryFakeImpl.delayTime = 0L
        launchFragmentInHiltContainer<PickEmployeeFragment> {
        }

        onView(withId(R.id.get_users_progress_bar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun handleGetAllEmployeesError_shouldHideProgressBarAndShowSnackbarWithCorrectMessage() {
        PickEmployeeRepositoryFakeImpl.isSuccess = false
        PickEmployeeRepositoryFakeImpl.delayTime = 0L
        launchFragmentInHiltContainer<PickEmployeeFragment> {
        }

        onView(withId(R.id.get_users_progress_bar)).check(matches(not(isDisplayed())))

        onView(withText("error message"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onEmployeeClick_shouldNavigateToPickMonthFragment() {
        PickEmployeeRepositoryFakeImpl.isSuccess = true
        PickEmployeeRepositoryFakeImpl.delayTime = 0L
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<PickEmployeeFragment> {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rec_view_employees)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FoldersAdapter.FoldersViewHolder>(
                0,
                ViewActions.click()
            )
        )

        assert(navController.currentDestination?.id == R.id.pickMonthFragment)
    }
}