package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.pick_route

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.di.launchFragmentInHiltContainer
import com.prizma_distribucija.prizmaadmin.feature_verify_route.data.repository.PickRouteRepositoryFakeImpl
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.EmployeeWithUnseenRoutes
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.FoldersAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class PickRouteFragmentTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun onCreate_shouldSetTitle() {
        val bundle = Bundle()
        val employee = Employee("1234", "lastName", "name", "userId")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)
        bundle.putParcelable("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        bundle.putInt("month", 1)
        launchFragmentInHiltContainer<PickRouteFragment>(bundle) {
        }

        onView(withId(R.id.tv_employee_name)).check(matches(withText("name lastName - ")))
        onView(withId(R.id.tv_month_name)).check(matches(withText("Siječanj (1)")))
    }

    @Test
    fun onGetRoutesLoading_shouldShowProgressBar() = runTest {
        val bundle = Bundle()
        val employee = Employee("1234", "lastName", "name", "userId")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)
        bundle.putParcelable("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        bundle.putInt("month", 1)

        PickRouteRepositoryFakeImpl.delayTime = 1000L
        PickRouteRepositoryFakeImpl.isSuccessful = false

        launchFragmentInHiltContainer<PickRouteFragment>(bundle) { }

        onView(withId(R.id.get_routes_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun onGetRoutesError_shouldHideProgressBarAndShowMessage() = runTest {
        val bundle = Bundle()
        val employee = Employee("1234", "lastName", "name", "userId")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)
        bundle.putParcelable("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        bundle.putInt("month", 1)

        PickRouteRepositoryFakeImpl.delayTime = 0L
        PickRouteRepositoryFakeImpl.isSuccessful = false

        launchFragmentInHiltContainer<PickRouteFragment>(bundle) { }

        onView(withId(R.id.get_routes_progress_bar)).check(matches(not(isDisplayed())))
        onView(withText("error message")).check(matches(isDisplayed()))
    }

    @Test
    fun onGetRoutesSuccess_shouldHideProgressBarAndDisplayRoutes() = runTest {
        val bundle = Bundle()
        val employee = Employee("1234", "lastName", "name", "userId")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)
        bundle.putParcelable("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        bundle.putInt("month", 1)

        PickRouteRepositoryFakeImpl.delayTime = 0L
        PickRouteRepositoryFakeImpl.isSuccessful = true

        lateinit var recView: RecyclerView
        launchFragmentInHiltContainer<PickRouteFragment>(bundle) {
            recView = this.requireActivity().findViewById(R.id.rec_view_routes)
        }

        onView(withId(R.id.get_routes_progress_bar)).check(matches(not(isDisplayed())))
        assert(recView.adapter?.itemCount == 1)
    }

    @Test
    fun onRouteClick_shouldNavigateToCheckRouteFragment() {
        val bundle = Bundle()
        val employee = Employee("1234", "lastName", "name", "id")
        val unseenRoutes = emptyList<Route>()
        val employeeWithUnseenRoutes = EmployeeWithUnseenRoutes(employee, unseenRoutes)

        bundle.putParcelable("employeeWithUnseenRoutes", employeeWithUnseenRoutes)
        bundle.putInt("month", 1)

        PickRouteRepositoryFakeImpl.delayTime = 0L
        PickRouteRepositoryFakeImpl.isSuccessful = true

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<PickRouteFragment>(bundle) {
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.pickRouteFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rec_view_routes))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<FoldersAdapter.FoldersViewHolder>(
                    0,
                    click()
                )
            )

        assert(navController.currentDestination?.id == R.id.checkRouteFragment)
    }
}