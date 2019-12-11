package id.scode.kadeooredoo.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 11 12/11/19 7:23 AM 2019
 * id.scode.kadeooredoo.ui.home
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour() {

        onView(withId(spinner))
            .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        onView(withText("Barcelona"))
            .check(matches(isDisplayed()))
        onView(withText("Barcelona")).perform(click())

        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        pressBack()

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))

        onView(withId(btm_fav)).perform(click())
    }
//    @Test
//    fun testRecyclerViewBehaviour() {
//
//        onView(withId(rv_list_team))
//            .check(matches(isDisplayed()))
//
//        onView(withId(rv_list_team)).perform(
//            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
//                10
//            )
//        )
//
//        onView(withId(rv_list_team)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
//        )
//    }
}