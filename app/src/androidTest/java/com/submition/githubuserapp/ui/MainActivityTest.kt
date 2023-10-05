package com.submition.githubuserapp.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.submition.githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testOpenDetailActivity() {

        Thread.sleep(4000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        Thread.sleep(2000)
        Espresso.pressBack()

        Thread.sleep(2000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )

        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    click()
                )
            )
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4,
                    click()
                )
            )
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    5,
                    click()
                )
            )
        Thread.sleep(2000)
        Espresso.pressBack()


    }
}