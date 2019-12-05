package id.scode.kadeooredoo.ui.detailLeague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.DetailLeagueResponse
import id.scode.kadeooredoo.data.db.network.responses.DetailMatchLeagueResponse
import id.scode.kadeooredoo.ui.detailLeague.view.DetailMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 06 12/6/19 4:48 AM 2019
 * id.scode.kadeooredoo.ui.detailLeague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class DetailMatchPresenter (
    private val view: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
){
    //behaviours getTeamList
    fun getDetailMatchList(league: String){
        view.showLoading()
        doAsync {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(
                        TheSportDbApi.getDetailMatchEventTeams(league)
                    ), DetailMatchLeagueResponse::class.java
                )
            uiThread {
                view.hideLoading()
                view.showDetailMatch(data.eventDetailMatches)
            }
        }
    }
}