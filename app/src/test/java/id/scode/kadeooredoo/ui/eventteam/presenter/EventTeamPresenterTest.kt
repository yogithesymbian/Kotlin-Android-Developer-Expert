package id.scode.kadeooredoo.ui.eventteam.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueAndTeamResponse
import id.scode.kadeooredoo.data.db.network.responses.PreviousLeagueAndTeamResponse
import id.scode.kadeooredoo.ui.eventteam.view.EventTeamView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 28 12/28/19 11:31 AM 2019
 * id.scode.kadeooredoo.ui.eventteam.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
class EventTeamPresenterTest {

    @Mock
    private lateinit var eventTeamView: EventTeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var eventTeamPresenter: EventTeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        eventTeamPresenter = EventTeamPresenter(
            eventTeamView,
            apiRepository,
            gson,
            ContextProviderTest()
        )
    }

    @Test
    fun getEventNextTeamList() {
        val eventNextMutableList: MutableList<EventNext> = mutableListOf()
        val response = NextLeagueAndTeamResponse(eventNextMutableList)
        val idTeam = "133601"

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

            eventTeamPresenter.getEventNextTeamList(idTeam)

            Mockito.verify(eventTeamView).showLoading()
            Mockito.verify(eventTeamView).showEventTeamNext(eventNextMutableList)
            Mockito.verify(eventTeamView).hideLoading()

        }
    }

    @Test
    fun getEventPrevTeamList() {

        val eventPrevMutableList: MutableList<EventPrevious> = mutableListOf()
        val response = PreviousLeagueAndTeamResponse(eventPrevious = eventPrevMutableList,eventPreviousTeam = eventPrevMutableList)
        val idTeam = "133601"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", PreviousLeagueAndTeamResponse::class.java))
                .thenReturn(response)

            eventTeamPresenter.getEventPrevTeamList(idTeam)

            Mockito.verify(eventTeamView).showLoading()
            Mockito.verify(eventTeamView).showEventTeamPrev(eventPrevMutableList)
            Mockito.verify(eventTeamView).hideLoading()

        }

    }
}