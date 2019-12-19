package id.scode.kadeooredoo.ui.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
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
class HomeActivityTest(private val testFunction: TestFunction) {
    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)
    /**
     * only this test run with position
     * @root_run
     */
    @Test
    fun allFunctionLoadTest() {
        testFunction.testHomeLabel() //L
        testFunction.testHomeComponentId() // C
        testFunction.testHomeBehaviourOnClickRecyclerView()
        testFunction.testDetailTeamLabel()
        testFunction.testDetailTeamComponentId()
        testFunction.testDetailTeamBehaviourOnClickFavorite()
        testFunction.testCheckResultFavorite()
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