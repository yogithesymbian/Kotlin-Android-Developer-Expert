package id.scode.kadeooredoo.ui.detailleague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.EXCEPTION_NULL
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueAndTeamResponse
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueSearchResponse
import id.scode.kadeooredoo.ui.detailleague.view.NextMatchLeagueView
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
 * Created on 11 12/11/19 7:49 AM 2019
 * id.scode.kadeooredoo.ui.detailleague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class NextPresenterTest {

    @Mock
    private lateinit var nextMatchLeagueView: NextMatchLeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var presenter: NextPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextPresenter(nextMatchLeagueView, apiRepository, gson, ContextProviderTest())
    }

    @Test
    fun getNextLeagueList() {

        val eventNextMutableList: MutableList<EventNext> = mutableListOf()
        val response = NextLeagueAndTeamResponse(eventNextMutableList)
        val idLeague = "4328"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", NextLeagueAndTeamResponse::class.java))
                .thenReturn(response)

            presenter.getNextLeagueList(idLeague)

            Mockito.verify(nextMatchLeagueView).showLoading()
            Mockito.verify(nextMatchLeagueView).showNextLeague(eventNextMutableList)
            Mockito.verify(nextMatchLeagueView).hideLoading()

        }

    }

    @Test
    fun getSearchNextLeagueList() {
        val eventSearchNextMutableList: MutableList<EventNext> = mutableListOf()
        val response = NextLeagueSearchResponse(eventSearchNextMutableList)
        val teamVsTeam = "bru"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", NextLeagueSearchResponse::class.java))
                .thenReturn(response)

            presenter.getSearchNextLeagueList(teamVsTeam)

            Mockito.verify(nextMatchLeagueView).showLoading()
            Mockito.verify(nextMatchLeagueView).hideLoading()

        }
    }

    @Test
    fun getNotFoundSearchNextLeagueList() {
        val eventSearchNextMutableList: MutableList<EventNext> = mutableListOf()
        val response = NextLeagueSearchResponse(eventSearchNextMutableList)
        val teamVsTeam = "bruu"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", NextLeagueSearchResponse::class.java))
                .thenReturn(response)

            presenter.getSearchNextLeagueList(teamVsTeam)
            // AnkoLogger has mock by defaultValue = true
            Mockito.verify(nextMatchLeagueView).showLoading()
            Mockito.verify(nextMatchLeagueView).exceptionNullObject("data_x $EXCEPTION_NULL")
            Mockito.verify(nextMatchLeagueView).hideLoading()

        }
    }

}