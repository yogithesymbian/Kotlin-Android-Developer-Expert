package id.scode.kadeooredoo.ui.detailleague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.CoroutineContextProvider
import id.scode.kadeooredoo.EXCEPTION_NULL
import id.scode.kadeooredoo.SPORT
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueResponse
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueSearchResponse
import id.scode.kadeooredoo.ui.detailleague.view.NextMatchLeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 05 12/5/19 11:23 AM 2019
 * id.scode.kadeooredoo.ui.detailLeague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class NextPresenter(
    private val viewMatch: NextMatchLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AnkoLogger {
    //behaviours getNextLeagueList
    fun getNextLeagueList(league: String) {
        viewMatch.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getNextMatchTeams(league)).await(),
                    NextLeagueResponse::class.java
                )
            viewMatch.hideLoading()
            viewMatch.showNextLeague(data.eventNexts)
        }
    }

    //behaviours getSearchPrevLeagueList
    fun getSearchNextLeagueList(teamVsTeam: String) {
        viewMatch.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.searchTeams(teamVsTeam)).await(),
                    NextLeagueSearchResponse::class.java
                )
            if (data.eventSearch.isNullOrEmpty()) {
                info("data_x $EXCEPTION_NULL")
                viewMatch.exceptionNullObject("data_x $EXCEPTION_NULL")
                viewMatch.hideLoading()
            } else {

                val filterOne = data.eventSearch.filter { it.strSport == SPORT }
                val filterTwo =
                    filterOne.filter { it.intHomeScore.isNullOrEmpty() } // next event not yet (score)
                val filterThree =
                    filterTwo.filter { it.intAwayScore.isNullOrEmpty() } // next event not yet (score)

                if (!filterThree.isNullOrEmpty()) {
                    viewMatch.showNextLeague(filterThree)
                    viewMatch.hideLoading()
                } else {
                    info("data_y $EXCEPTION_NULL")
                    viewMatch.exceptionNullObject("data_y $EXCEPTION_NULL")
                    viewMatch.hideLoading()
                }
            }
        }
    }
}