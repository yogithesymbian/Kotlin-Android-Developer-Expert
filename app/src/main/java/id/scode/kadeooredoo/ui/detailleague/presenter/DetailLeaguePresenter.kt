package id.scode.kadeooredoo.ui.detailleague.presenter

import com.google.gson.Gson
import id.scode.kadeooredoo.CoroutineContextProvider
import id.scode.kadeooredoo.SPORT
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.data.db.network.TheSportDbApi
import id.scode.kadeooredoo.data.db.network.responses.DetailLeagueResponse
import id.scode.kadeooredoo.ui.detailleague.view.DetailLeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 03 12/3/19 3:00 PM 2019
 * id.scode.kadeooredoo.ui.detailLeague.presenter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    //behaviours getLeagueTeamList
    fun getDetailLeagueList(league: String) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequestAsync(TheSportDbApi.getDetailLeagueTeams(league)).await(),
                    DetailLeagueResponse::class.java
                )

            view.showDetailLeague(data.leagues.filter { it.strSport == SPORT })
            view.hideLoading()
        }
    }
}