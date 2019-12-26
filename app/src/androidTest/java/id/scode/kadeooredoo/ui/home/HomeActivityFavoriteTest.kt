package id.scode.kadeooredoo.ui.home

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.EspressoIdlingResource
import id.scode.kadeooredoo.testhelper.TestEspresso
import id.scode.kadeooredoo.ui.home.ui.HomeActivity
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

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


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // ref google to order function by nameAscending
@RunWith(AndroidJUnit4::class)
class HomeActivityFavoriteTest {

    private lateinit var testEspresso: TestEspresso

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java)


    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }

    /**
     * make sure you don't have favorite item
     * because the snackbar ViewMatch is added to favorite, not removed favorite [ state ]
     */
    @Test
    fun testHomeActivityAddCatalogueToFavorite() {

        testEspresso = TestEspresso()

        testEspresso.testHomeLabel()
        testEspresso.testHomeComponentId()
        testEspresso.testHomeBehaviourRecycler()
        testEspresso.testDetailTeamComponentId()
        testEspresso.testDetailTeamBehaviourAddToFavorite()
        testEspresso.testCheckResultFavorite()

    }

}