package id.scode.kadeooredoo.ui.classificationmatch.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.ContextProviderTest
import id.scode.kadeooredoo.data.db.entities.Table
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.responses.ClassificationMatchResponse
import id.scode.kadeooredoo.ui.classificationmatch.view.ClassificationMatchView
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
 * Created on 28 12/28/19 11:26 AM 2019
 * id.scode.kadeooredoo.ui.classificationmatch.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
class ClassificationMatchPresenterTest {

    @Mock
    private lateinit var classificationMatchView: ClassificationMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    @Mock
    private lateinit var classificationMatchPresenter: ClassificationMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        classificationMatchPresenter = ClassificationMatchPresenter(
            classificationMatchView,
            apiRepository,
            gson,
            ContextProviderTest()
        )
    }

    @Test
    fun getClassificationMatchTable() {

        val leagues: MutableList<Table> = mutableListOf()
        val response = ClassificationMatchResponse(leagues)
        val idLeague = "4328"

        runBlocking {
            Mockito
                .`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito
                .`when`(apiResponse.await())
                .thenReturn("")

            Mockito
                .`when`(gson.fromJson("", ClassificationMatchResponse::class.java))
                .thenReturn(response)

            classificationMatchPresenter.getClassificationMatchTable(idLeague)

            Mockito.verify(classificationMatchView).showLoading()
            Mockito.verify(classificationMatchView).showClassificationMatchTable(leagues)
            Mockito.verify(classificationMatchView).hideLoading()

        }

    }
}