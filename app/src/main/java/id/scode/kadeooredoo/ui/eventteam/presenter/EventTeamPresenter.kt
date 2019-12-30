package id.scode.kadeooredoo.ui.eventteam.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.CoroutineContextProvider
import id.scode.kadeooredoo.EspressoIdlingResource
import id.scode.kadeooredoo.TESTING_FLAG
import id.scode.kadeooredoo.TESTING_FLAG_MATCH
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueAndTeamResponse
import id.scode.kadeooredoo.data.db.network.responses.PreviousLeagueAndTeamResponse
import id.scode.kadeooredoo.ui.eventteam.view.EventTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 6:07 AM 2019
 * id.scode.kadeooredoo.ui.eventteam.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
class EventTeamPresenter (
    private val view: EventTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AnkoLogger {

    fun getEventNextTeamList(idTeam: String) {

        if (TESTING_FLAG == TESTING_FLAG_MATCH)
            EspressoIdlingResource.increment()

        view.showLoading()

        GlobalScope.launch(context.main) {

            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getNextMatchTeams(idTeam)).await(),
                    NextLeagueAndTeamResponse::class.java
                )
            view.hideLoading()
            view.showEventTeamNext(data.eventNexts)

        }
        if (TESTING_FLAG == TESTING_FLAG_MATCH)
            if (!EspressoIdlingResource.idlingresource.isIdleNow)
                EspressoIdlingResource.decrement()
    }

    fun getEventPrevTeamList(idTeam: String) {

        if (TESTING_FLAG == TESTING_FLAG_MATCH)
            EspressoIdlingResource.increment()

        view.showLoading()

        GlobalScope.launch(context.main) {

            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getPreviousMatchTeams(idTeam)).await(),
                    PreviousLeagueAndTeamResponse::class.java
                )
            view.hideLoading()
            if (data.eventPreviousTeam != null){
                view.showEventTeamPrev(data.eventPreviousTeam)
            }

        }
        if (TESTING_FLAG == TESTING_FLAG_MATCH)
            if (!EspressoIdlingResource.idlingresource.isIdleNow)
                EspressoIdlingResource.decrement()
    }
}