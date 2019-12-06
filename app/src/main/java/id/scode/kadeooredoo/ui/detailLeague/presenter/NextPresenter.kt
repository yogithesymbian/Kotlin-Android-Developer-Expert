package id.scode.kadeooredoo.ui.detailLeague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueResponse
import id.scode.kadeooredoo.data.db.network.responses.NextLeagueSearchResponse
import id.scode.kadeooredoo.ui.detailLeague.view.NextMatchLeagueView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
class NextPresenter (
    private val viewMatch: NextMatchLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
){
    //behaviours getNextLeagueList
    fun getNextLeagueList(league: String){
        viewMatch.showLoading()
        doAsync {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(
                        TheSportDbApi.getNextMatchTeams(league)
                    ), NextLeagueResponse::class.java
                )
            uiThread {
                viewMatch.hideLoading()
                viewMatch.showNextLeague(data.eventNexts)
            }
        }
    }
    //behaviours getSearchPrevLeagueList
    fun getSearchNextLeagueList(teamVsTeam: String){
        viewMatch.showLoading()
        doAsync {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(
                        TheSportDbApi.searchTeams(teamVsTeam)
                    ), NextLeagueSearchResponse::class.java
                )
            uiThread {
                viewMatch.hideLoading()
                viewMatch.showNextLeague(data.eventSearch)
            }
        }
    }
}