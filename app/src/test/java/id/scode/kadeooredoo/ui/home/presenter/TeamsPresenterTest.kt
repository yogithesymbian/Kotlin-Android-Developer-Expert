package id.scode.kadeooredoo.ui.home.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.TeamResponse
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 10 12/10/19 9:26 PM 2019
 * id.scode.kadeooredoo.ui.home.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class TeamsPresenterTest {

    @Mock
    private lateinit var teamsView: TeamsView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(teamsView, apiRepository, gson, ContextProviderTest())
    }

    // test function has same order in [TeamPresenter.kt]
    @Test
    fun getLeagueTeamList() {

        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "English Premiere League"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(response)

            presenter.getLeagueTeamList(league)

            Mockito.verify(teamsView).showLoading()
            Mockito.verify(teamsView).showTeamList(teams)
            Mockito.verify(teamsView).hideLoading()

        }

    }

    @Test
    fun getDetailLeagueTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idHomeTeam = "133619"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(response)


            presenter.getDetailLeagueTeamList(idHomeTeam)

            Mockito.verify(teamsView).hideLoading()
            Mockito.verify(teamsView).showTeamList(teams)
            Mockito.verify(teamsView).hideLoading()

        }
    }

    @Test
    fun getDetailLeagueTeamAwayList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idAwayTeam = "133599"
        val idAwayTeamCheck = "133599"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(response)

            presenter.getDetailLeagueTeamAwayList(idAwayTeam)

            Mockito.verify(teamsView).hideLoading()
            Mockito.verify(teamsView).showTeamAwayList(teams)
            Mockito.verify(teamsView).hideLoading()

        }
    }
}