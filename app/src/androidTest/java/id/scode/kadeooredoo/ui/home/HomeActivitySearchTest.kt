package id.scode.kadeooredoo.ui.home

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import id.scode.kadeooredoo.EspressoIdlingResource
import id.scode.kadeooredoo.testhelper.*
import id.scode.kadeooredoo.testhelper.TestEspresso.Companion.NEXT_TEXT_FOUND
import id.scode.kadeooredoo.testhelper.TestEspresso.Companion.NEXT_TEXT_NOT_FOUND
import id.scode.kadeooredoo.testhelper.TestEspresso.Companion.PREV_TEXT_FOUND
import id.scode.kadeooredoo.testhelper.TestEspresso.Companion.PREV_TEXT_NOT_FOUND
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
class HomeActivitySearchTest {

    private lateinit var label: LabelTestEspresso
    private lateinit var labelAndData: LabelDataTestEspresso
    private lateinit var componentId: CompIdTestEspresso
    private lateinit var behaviour: BehaviourTestEspresso
    private lateinit var other: OtherTestEspresso

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


    @Test
    fun testHomeActivitySearch() {

        // initialize the fun
        label = LabelTestEspresso()
        labelAndData = LabelDataTestEspresso()
        componentId = CompIdTestEspresso()
        behaviour = BehaviourTestEspresso()
        other = OtherTestEspresso()

        // let's do it
        label.testHomeLabel() // see text on Home
        componentId.testHomeComponentId() // looking for id on home
        behaviour.testHomeBehaviourDetailLeague() // click detail league on home

        componentId.testDetailMatchLeagueComponentId() // looking for id on detailMatchLeague
        labelAndData.testDetailMatchLeagueLabelAndData() // see text and data on detailMatchLeague
        behaviour.testDetailMatchLeagueBehaviour() // click Float_Button ( float_social )
        behaviour.testDetailMatchLeagueBehaviourToPrev() // navigation to previous fragment

        label.testPrevMatchLeagueLabel() // see text on PrevMatchLeague
        componentId.testPrevMatchLeagueComponentId() // looking for id on PrevMatchLeague
        behaviour.testPreviousMatchLeagueBehaviourSearch(PREV_TEXT_FOUND) // test search "bru" | item found
        behaviour.testPreviousMatchLeagueBehaviourSearch(PREV_TEXT_NOT_FOUND) // test search "bruuuu" | item not found
        behaviour.testDetailMatchLeagueBehaviourToNext()

        label.testNextMatchLeagueLabel() // see text on NextMatchLeague
        componentId.testNextMatchLeagueComponentId() // looking for id on NextMatchLeague
        behaviour.testNextMatchLeagueBehaviourSearch(NEXT_TEXT_FOUND) // just duplicate function prev , more readable in sending param position
        behaviour.testNextMatchLeagueBehaviourSearch(NEXT_TEXT_NOT_FOUND) // test search "chelx" | item not found

        behaviour.gotoHomeFromNextMatch()
    }

}