package com.prizma_distribucija.prizmaadmin.core.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


fun atPositionOnView(
    position: Int, itemMatcher: Matcher<View>,
    targetViewId: Int
): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView: View = viewHolder!!.itemView.findViewById(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}
