package com.prizma_distribucija.prizmaadmin.feature_verify_route.presentation.check_route

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prizma_distribucija.prizmaadmin.R
import com.prizma_distribucija.prizmaadmin.core.di.launchFragmentInHiltContainer
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Employee
import com.prizma_distribucija.prizmaadmin.feature_verify_route.domain.model.Route
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CheckRouteFragmentTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private fun getDefaultBundle(): Bundle {
        val route = Route(
            "",
            "0.0 km/h",
            1,
            "0.1 km",
            1, emptyList(),
            "10:00",
            "09:00",
            "userId",
            2022,
            false
        )

        val employee = Employee("1234", "lastName", "name", "userId")

        val bundle = Bundle()
        bundle.putParcelable("route", route)
        bundle.putParcelable("employee", employee)

        return bundle
    }

    @Test
    fun onCreate_setUiData() {
        launchFragmentInHiltContainer<CheckRouteFragment>(getDefaultBundle()) {

        }

        onView(withId(R.id.constraint_layout)).perform(click())
        onView(withId(R.id.tv_distance)).check(matches(withText("0.1 km")))
        onView(withId(R.id.tv_folder_title)).check(matches(withText("name lastName")))
        onView(withId(R.id.tv_avg_speed)).check(matches(withText("0.0 km/h")))
        onView(withId(R.id.tv_work_time)).check(matches(withText("09:00 - 10:00")))
    }

    @Test
    fun onStatsClick_shouldMakeDetailedStatsVisible() {
        launchFragmentInHiltContainer<CheckRouteFragment>(getDefaultBundle()) {

        }
        onView(withId(R.id.constraint_layout_stats)).check(matches(not(isDisplayed())))
        onView(withId(R.id.constraint_layout)).perform(click())
        onView(withId(R.id.constraint_layout_stats)).check(matches(isDisplayed()))
    }
}