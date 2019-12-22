package id.scode.kadeooredoo.testhelper

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.ui.home.HomeActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

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

/**
 * Description Test [ ATTENTION ]
 * View Label Home
 * Behaviour Item Home
 * !NOTE LEAK MEMORY | tips : breakPoint / red line shortcut find | delete this code after espresso test
 * #TeamsFragment
 * Line 136 - 137
 * Line 148 - 149
 * Line 253 - 256
 * #TeamsDetailActivity
 * Line 122
 * Line 244 - 247
 * #DashboardFragment
 * Line 72
 * Line 179 - 183
 * #NextMatchLeagueFragment
 * Line 73
 * Line 157 - 160
 * Line 181 - 184
 * #PreviousMatchLeagueFragment
 * Line 76
 * Line 150
 * Line 164 - 167
 * Line 188 - 191
 */

/**
 * CHEAT_SHEET
 * https://developer.android.com/training/testing/espresso/cheat-sheet
 */

/**
 * search function list
 * -------------------------
 * LabelFunction
 * LabelAndDataFunction
 * ComponentIdFunction
 * BehaviourFunction
 * OtherFunction
 * -------------------------
 * tips use fold and unfold
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // ref google to order function by nameAscending
@RunWith(AndroidJUnit4::class)
class TestEspresso : AnkoLogger{

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java
    )

    // #LabelFunction -----------------------------
    @Test
    fun testHomeLabel() {

        Espresso.onView(ViewMatchers.withText("League Football"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("English Premier League"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun aDaTestDetailTeamLabel() { // Problem views are marked with '****MATCHES****' below.
        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testPrevMatchLeagueLabel() {

        Espresso.onView(ViewMatchers.withText("Event Past League"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testPrevMatchLeagueLabelSearchResult() {

        Espresso.onView(ViewMatchers.withText("Club Brugge"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testNextMatchLeagueLabelSearchResult() {

        Espresso.onView(ViewMatchers.withText("Club Brugge"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testNextMatchLeagueLabel() {

        Espresso.onView(ViewMatchers.withText("Event Next League"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    // #LabelFunction end of -----------------------


    // #LabelAndDataFunction -----------------------
    @Test
    fun testDetailMatchLeagueLabelAndData() {

        Espresso.onView(ViewMatchers.withText("Country England"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Soccer"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    // #LabelAndDataFunction -----------------------


    // #ComponentIdFunction ------------------------
    @Test
    fun testHomeComponentId() {

        Espresso.onView(ViewMatchers.withId(R.id.toolbar_home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.spinner))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.btn_det_1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.bottom_navigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testDetailTeamComponentId() {

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testDetailMatchLeagueComponentId() {

        Espresso.onView(ViewMatchers.withId(R.id.float_social_media))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.navigation_next))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.navigation_previous))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testPrevMatchLeagueComponentId() {

        Espresso.onView(ViewMatchers.withId(R.id.option_search_previous))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testNextMatchLeagueComponentId() {

        Espresso.onView(ViewMatchers.withId(R.id.option_search_next))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    // #ComponentIdFunction end of -------------------


    // #BehaviourFunction ----------------------------
    @Test
    fun testHomeBehaviourRecycler() {

        Espresso.onView(ViewMatchers.withId(R.id.spinner)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Spanish La Liga")).perform(ViewActions.click())


        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_list_team))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_list_team)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2)
        )

        Espresso.onView(ViewMatchers.withText("Ath Bilbao")).perform(ViewActions.click())
    }

    @Test
    fun testHomeBehaviourDetailLeague() {

        Espresso.onView(ViewMatchers.withId(R.id.btn_det_1)).perform(ViewActions.click())

    }

    @Test
    fun testDetailTeamBehaviourAddToFavorite() {

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Added to favorite"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.pressBack()

    }

    @Test
    fun testDetailMatchLeagueBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.float_social_media)).perform(ViewActions.click())

    }

    @Test
    fun testDetailMatchLeagueBehaviourToPrev() {

        Espresso.onView(ViewMatchers.withId(R.id.navigation_previous)).perform(ViewActions.click())

    }

    @Test
    fun testDetailMatchLeagueBehaviourToNext() {

        Espresso.onView(ViewMatchers.withId(R.id.navigation_next)).perform(ViewActions.click())

    }

    @Test
    fun testPreviousMatchLeagueBehaviourSearch(text: String) {

        info("search : $text")

        Espresso.onView(ViewMatchers.withId(R.id.option_search_previous))
            .perform(ViewActions.click())

        // ref https://stackoverflow.com/questions/42561245/can-one-test-support-v7-searchview-with-espresso-on-android
        // ref https://www.dicoding.com/academies/55/discussions/14079
        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
            .perform(ViewActions.typeText(text))
            .perform(ViewActions.pressImeActionButton())

        Thread.sleep(10000) // wait a second , i want see it work or nah | without onData CoreMatcher withItemContent
        // soon will customResult and see by system like as above reference

        when (text) {
            PREV_TEXT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.rv_prev_match_leagues))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


            }
            PREV_TEXT_NOT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.img_exception_search_nf_fp))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        }
        // double back | left on searchView
        Espresso.pressBack()
        Espresso.pressBack()

    }

    @Test
    fun testNextMatchLeagueBehaviourSearch(text: String) {

        info("search : $text")

        Espresso.onView(ViewMatchers.withId(R.id.option_search_next))
            .perform(ViewActions.click())

        // ref https://stackoverflow.com/questions/42561245/can-one-test-support-v7-searchview-with-espresso-on-android
        // ref https://www.dicoding.com/academies/55/discussions/14079
        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
            .perform(ViewActions.typeText(text))
            .perform(ViewActions.pressImeActionButton())

        Thread.sleep(10000) // wait a second , i want see it work or nah | without onData CoreMatcher withItemContent
        // soon will customResult and see by system like as above reference

        when (text) {
            NEXT_TEXT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.rv_next_match_leagues))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


            }
            NEXT_TEXT_NOT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.img_exception_search_nf_fn))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        }
        // double back | left on searchView
        Espresso.pressBack()
        Espresso.pressBack()

    }
    // #BehaviourFunction end of ---------------------


    // #OtherFunction-----------------------------------------
    @Test
    fun testCheckResultFavorite() {

        Espresso.onView(ViewMatchers.withId(R.id.btm_fav)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    // #OtherFunction end of ---------------------------------


    companion object{

        const val PREV_TEXT_FOUND = "bru"
        const val PREV_TEXT_NOT_FOUND = "bruuuu"

        const val NEXT_TEXT_FOUND = "chel"
        const val NEXT_TEXT_NOT_FOUND = "chelx"
    }
}