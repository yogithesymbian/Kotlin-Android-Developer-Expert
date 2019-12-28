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
import id.scode.kadeooredoo.ui.home.ui.HomeActivity
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


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // ref google to order function by nameAscending
@RunWith(AndroidJUnit4::class)
class BehaviourTestEspresso : AnkoLogger {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java
    )

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
    fun testDetailMatchLeagueClassificationBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.img_classification_match))
            .perform(ViewActions.click())

    }

    @Test
    fun testOpenTagClassificationBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.btn_lbl_tag)).perform(ViewActions.click())

    }

    @Test
    fun testChooseTagClassificationBehaviour() {


        Thread.sleep(100)

        Espresso.onView(ViewMatchers.withId(R.id.spinner_classification))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Spanish La Liga")).perform(ViewActions.click())


        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.onView(ViewMatchers.withId(R.id.rv_classification_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_classification_match)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2)
        )

        Espresso.onView(ViewMatchers.withText("Ath Bilbao")).perform(ViewActions.click())

    }

    @Test
    fun testFabBackClassificationBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.fab_classification_back))
            .perform(ViewActions.click())

    }

    @Test
    fun testImgEventTeamDetailBehaviour() {

        Espresso.onView(ViewMatchers.withId(R.id.img_event_team_detail))
            .perform(ViewActions.click())

    }

    @Test
    fun testWaitBackBehaviour() {
        Espresso.pressBack()
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

    @Test
    fun testHomeSearch(text: String) {

        info("search : $text")

        Espresso.onView(ViewMatchers.withId(R.id.option_search_home))
            .perform(ViewActions.click())

        // ref https://stackoverflow.com/questions/42561245/can-one-test-support-v7-searchview-with-espresso-on-android
        // ref https://www.dicoding.com/academies/55/discussions/14079
        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
            .perform(ViewActions.typeText(text))
            .perform(ViewActions.pressImeActionButton())

        when (text) {
            HOME_TEXT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.rv_list_team))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


            }
            HOME_TEXT_NOT_FOUND -> {
                Espresso.onView(ViewMatchers.withId(R.id.img_exception_search_team))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        }

        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_close_btn))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_close_btn))
            .perform(ViewActions.click())

    }

    @Test
    fun gotoHomeFromNextMatch() {
        Espresso.pressBack()
    }

    @Test
    fun gotoHomeFromDashboardFragment() {
        Espresso.pressBack()
        Espresso.pressBack()
    }
    // #BehaviourFunction end of ---------------------

    companion object {

        const val PREV_TEXT_FOUND = "bru"
        const val PREV_TEXT_NOT_FOUND = "bruuuu"

        const val NEXT_TEXT_FOUND = "chel"
        const val NEXT_TEXT_NOT_FOUND = "chelx"

        const val HOME_TEXT_FOUND = "arsenal"
        const val HOME_TEXT_NOT_FOUND = "arsenalx"
    }

}