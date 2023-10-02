package com.ryan.githubuserapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ryan.githubuserapp.ui.activity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testActionBarTitle() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToFavorites() {
        onView(withId(R.id.fab)).perform(click())
        onView(withText(R.string.fav_activity)).check(matches(isDisplayed()))
    }
}