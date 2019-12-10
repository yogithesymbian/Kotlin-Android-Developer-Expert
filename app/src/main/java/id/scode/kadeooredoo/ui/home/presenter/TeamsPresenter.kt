package id.scode.kadeooredoo.ui.home.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.SPORT
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.TeamResponse
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 04 11/4/19 7:09 AM 2019
 * id.scode.kadeooredoo.ui.home.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    //behaviours getLeagueTeamList
    fun getLeagueTeamList(league: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {

            val data =
                gson.fromJson(
                    apiRepository.doRequest(TheSportDbApi.getLeagueTeams(league)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamList(data.team)

        }
    }

    //behaviours getLeagueTeamList
    fun getDetailLeagueTeamList(idTeams: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(TheSportDbApi.getLookupTeams(idTeams)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamList(data.team?.filter { it.strSport == SPORT })
        }
    }

    //behaviours getLeagueTeamList
    fun getDetailLeagueTeamAwayList(idTeams: String) {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(TheSportDbApi.getLookupTeams(idTeams)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamAwayList(data.team?.filter { it.strSport == SPORT })
        }
    }

}