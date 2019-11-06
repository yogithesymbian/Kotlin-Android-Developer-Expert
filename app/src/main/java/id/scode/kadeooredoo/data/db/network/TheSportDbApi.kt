package id.scode.kadeooredoo.data.db.network

import android.net.Uri
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
 */
object TheSportDbApi {
    fun getTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("search_all_teams.php")
            .appendQueryParameter("l", league)
            .build()
            .toString()
    }

//    Detail liga: https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id={idLeague}
//
//    Daftar next match: https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id={idLeague}
//
//    Daftar previous match:  https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id={idLeague}
//
//    Detail pertandingan: https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id={idEvent}
//
//    Pencarian pertandingan: https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e={query}
}