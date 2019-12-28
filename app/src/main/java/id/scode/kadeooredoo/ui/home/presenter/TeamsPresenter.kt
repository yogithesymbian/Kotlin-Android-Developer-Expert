package id.scode.kadeooredoo.ui.home.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.CoroutineContextProvider
import id.scode.kadeooredoo.EXCEPTION_NULL
import id.scode.kadeooredoo.SPORT
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.TeamResponse
import id.scode.kadeooredoo.ui.detailleague.adapter.RvNextMatchLeagueAdapter
import id.scode.kadeooredoo.ui.detailleague.adapter.RvPrevMatchLeagueAdapter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

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
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AnkoLogger {

    fun getLeagueTeamList(league: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {

            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getLeagueTeams(league)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamList(data.team)

        }
    }

    fun getDetailLeagueTeamList(
        idTeams: String,
        position: Int? =null,
        holder: RvPrevMatchLeagueAdapter.ViewHolder?= null,
        holder1: RvNextMatchLeagueAdapter.ViewHolder?= null
    ) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getLookupTeams(idTeams)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamList(data.team?.filter { it.strSport == SPORT }, idTeams, position, holder, holder1)
        }
    }

    fun getDetailLeagueTeamAwayList(
        idTeams: String,
        position: Int? =null,
        holder: RvPrevMatchLeagueAdapter.ViewHolder?= null,
        holder1: RvNextMatchLeagueAdapter.ViewHolder?= null
    ) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getLookupTeams(idTeams)).await(),
                    TeamResponse::class.java
                )
            view.hideLoading()
            view.showTeamAwayList(data.team?.filter { it.strSport == SPORT }, idTeams, position, holder, holder1)
        }
    }

    fun getSearchTeams(teamsQuery: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getSearchTeams(teamsQuery)).await(),
                    TeamResponse::class.java
                )
            if (data.team.isNullOrEmpty()) {
                info("data_x $EXCEPTION_NULL")
                view.exceptionNullObject("data_x $EXCEPTION_NULL")
                view.hideLoading()
            } else {
                val filterOne = data.team.filter { it.strSport == SPORT }

                if (!filterOne.isNullOrEmpty()) {
                    view.showTeamList(filterOne)
                    view.hideLoading()
                } else {
                    info("data_y $EXCEPTION_NULL")
                    view.exceptionNullObject("data_y $EXCEPTION_NULL")
                    view.hideLoading()
                }
            }
        }
    }

}