package id.scode.kadeooredoo.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 12 12/12/19 5:35 AM 2019
 * id.scode.kadeooredoo.ui.home
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class TestFunction {

    // above function just init for allFunctionLoadTest
    @Test
    fun testHomeLabel(){

        onView(withText("League Football"))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withText("English Premier League"))
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun testHomeComponentId(){

        onView(withId(R.id.toolbar_home))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.spinner))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.bottom_navigation))
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun testHomeBehaviourOnClickRecyclerView() {

        onView(withId(R.id.spinner)).perform(ViewActions.click())

        onView(withText("Spanish La Liga")).perform(ViewActions.click())

        Thread.sleep(10000)

        onView(withText("Ath Bilbao"))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withText("Ath Bilbao")).perform(ViewActions.click())


    }

    @Test
    fun testDetailTeamLabel(){

        onView(withText("Ath Bilbao"))
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun testDetailTeamComponentId(){

        onView(withId(R.id.add_to_favorite))
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun testDetailTeamBehaviourOnClickFavorite(){

        onView(withId(R.id.add_to_favorite)).perform(ViewActions.click())

        onView(withText("Added to favorite"))
            .check(ViewAssertions.matches(isDisplayed()))

        pressBack()

    }

    @Test
    fun testCheckResultFavorite(){

        testHomeLabel()
        testHomeComponentId()

        onView(withId(R.id.btm_fav)).perform(ViewActions.click())

    }


}