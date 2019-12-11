package id.scode.kadeooredoo.data.db.network

import id.scode.kadeooredoo.BuildConfig

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 04 11/4/19 7:06 AM 2019
 * id.scode.kadeooredoo.data.db.network
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 *
 *
 */
object TheSportDbApi {

    private const val API = "api"
    private const val VERSI = "v1"
    private const val JSON = "json"

    fun getLeagueTeams(idLeague: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=" + idLeague
    }


    fun getLookupTeams(idTeam: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=" + idTeam
    }

    fun getDetailLeagueTeams(idLeague: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/lookupleague.php?id=" + idLeague
    }

    fun getNextMatchTeams(idLeague: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=" + idLeague
    }

    fun getPreviousMatchTeams(idLeague: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=" + idLeague
    }


    fun getDetailMatchEventTeams(idEvent: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/lookupevent.php?id=" + idEvent
    }

    fun searchTeams(teamVsTeam: String): String {
        return BuildConfig.BASE_URL +
                "$API/$VERSI/$JSON/${BuildConfig.TSDB_API_KEY}" +
                "/searchevents.php?e=" + teamVsTeam
    }
}