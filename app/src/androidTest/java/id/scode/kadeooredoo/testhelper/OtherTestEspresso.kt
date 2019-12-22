package id.scode.kadeooredoo.testhelper

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.ui.home.HomeActivity
import org.jetbrains.anko.AnkoLogger
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
class OtherTestEspresso : AnkoLogger{

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java
    )

    // #OtherFunction-----------------------------------------
    @Test
    fun testCheckResultFavorite() {

        Espresso.onView(ViewMatchers.withId(R.id.btm_fav)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Ath Bilbao"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    // #OtherFunction end of ---------------------------------
}