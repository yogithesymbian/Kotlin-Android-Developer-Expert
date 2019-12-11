package id.scode.kadeooredoo.ui.detailleague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.data.db.entities.EventDetailMatch
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.DetailMatchLeagueResponse
import id.scode.kadeooredoo.ui.detailleague.view.DetailMatchView
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
 * Created on 11 12/11/19 7:44 AM 2019
 * id.scode.kadeooredoo.ui.detailleague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class DetailMatchPresenterTest {

    @Mock
    private lateinit var detailMatchView: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(detailMatchView, apiRepository, gson, ContextProviderTest())
    }

    @Test
    fun getDetailMatchList() {

        val eventDetailMatchesMutableList: MutableList<EventDetailMatch> = mutableListOf()
        val response = DetailMatchLeagueResponse(eventDetailMatchesMutableList)
        val idEvent = "602269"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", DetailMatchLeagueResponse::class.java))
                .thenReturn(response)

            presenter.getDetailMatchList(idEvent)

            Mockito.verify(detailMatchView).showLoading()
            Mockito.verify(detailMatchView).showDetailMatch(eventDetailMatchesMutableList)
            Mockito.verify(detailMatchView).hideLoading()

        }

    }
}