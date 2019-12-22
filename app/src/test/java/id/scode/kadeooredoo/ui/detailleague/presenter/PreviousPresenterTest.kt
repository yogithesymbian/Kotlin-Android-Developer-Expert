package id.scode.kadeooredoo.ui.detailleague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.EXCEPTION_NULL
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.PreviousLeagueResponse
import id.scode.kadeooredoo.data.db.network.responses.PreviousLeagueSearchResponse
import id.scode.kadeooredoo.ui.detailleague.view.PreviousMatchLeagueView
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
 * Created on 11 12/11/19 8:24 AM 2019
 * id.scode.kadeooredoo.ui.detailleague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class PreviousPresenterTest {

    @Mock
    private lateinit var previousMatchLeagueView: PreviousMatchLeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var presenter: PreviousPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PreviousPresenter(previousMatchLeagueView, apiRepository, gson, ContextProviderTest())
    }

    @Test
    fun getPreviousLeagueList() {

        val eventPrevMutableList: MutableList<EventPrevious> = mutableListOf()
        val response = PreviousLeagueResponse(eventPrevMutableList)
        val idLeague = "4328"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", PreviousLeagueResponse::class.java))
                .thenReturn(response)

            presenter.getPreviousLeagueList(idLeague)

            Mockito.verify(previousMatchLeagueView).showLoading()
            Mockito.verify(previousMatchLeagueView).showPreviousLeague(eventPrevMutableList)
            Mockito.verify(previousMatchLeagueView).hideLoading()

        }

    }

    @Test
    fun getSearchPreviousLeagueList() {
        val eventSearchNextMutableList: MutableList<EventPrevious> = mutableListOf()
        val response = PreviousLeagueSearchResponse(eventSearchNextMutableList)
        val teamVsTeam = "bru"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", PreviousLeagueSearchResponse::class.java))
                .thenReturn(response)

            presenter.getSearchPreviousLeagueList(teamVsTeam)

            Mockito.verify(previousMatchLeagueView).showLoading()
            Mockito.verify(previousMatchLeagueView).hideLoading()

        }
    }

    @Test
    fun getNotFoundSearchPreviousLeagueList() {
        val eventSearchNextMutableList: MutableList<EventPrevious> = mutableListOf()
        val response = PreviousLeagueSearchResponse(eventSearchNextMutableList)
        val teamVsTeam = "bruu"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", PreviousLeagueSearchResponse::class.java))
                .thenReturn(response)

            presenter.getSearchPreviousLeagueList(teamVsTeam)
            // AnkoLogger has mock by defaultValue = true
            Mockito.verify(previousMatchLeagueView).showLoading()
            Mockito.verify(previousMatchLeagueView).exceptionNullObject("data_x $EXCEPTION_NULL")
            Mockito.verify(previousMatchLeagueView).hideLoading()

        }
    }
}