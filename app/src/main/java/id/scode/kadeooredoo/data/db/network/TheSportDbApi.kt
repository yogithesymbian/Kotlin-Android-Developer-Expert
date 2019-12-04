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
 *
 * Beberapa fitur yang harus dimodifikasi atau ditambahkan pada aplikasi:

Daftar Liga
Dikarenakan pada TheSportDB tidak terdapat endpoint khusus untuk liga sepak bola, maka data yang ditampilkan boleh tetap dari lokal.
Detail Liga
Menampilkan detail liga dengan data yang diperoleh dari API. Anda bisa memanfaatkan id liga sebagai parameternya.
Jadwal Pertandingan
Menampilkan daftar pertandingan (Next Match dan Previous Match) pada liga yang dipilih oleh pengguna dengan data yang diperoleh dari API.

Detail Pertandingan
Menampilkan detail pertandingan (logo tim, skor dan informasi lainnya) dari pertandingan yang dipilih dengan data yang diperoleh dari API.

Pencarian Pertandingan
Fitur untuk melakukan pencarian pertandingan.
 */
object TheSportDbApi {

    private const val API = "api"
    private const val VERSI = "v1"
    private const val JSON = "json"

//    Detail liga: https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id={idLeague}
//
//    Daftar next match: https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id={idLeague}
//
//    Daftar previous match:  https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id={idLeague}
//
//    Detail pertandingan: https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id={idEvent}
//
//    Pencarian pertandingan: https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e={query}

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

    fun getDetailLeagueTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath(API)
            .appendPath(VERSI)
            .appendPath(JSON)
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupleague.php")
            .appendQueryParameter("id", league)
            .build()
            .toString()
    }

    fun getNextMatchTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath(API)
            .appendPath(VERSI)
            .appendPath(JSON)
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", league)
            .build()
            .toString()
    }

    fun getPreviousMatchTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath(API)
            .appendPath(VERSI)
            .appendPath(JSON)
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id", league)
            .build()
            .toString()
    }

    fun getDetailEventTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath(API)
            .appendPath(VERSI)
            .appendPath(JSON)
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id", league)
            .build()
            .toString()
    }

    fun searchTeams(league: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath(API)
            .appendPath(VERSI)
            .appendPath(JSON)
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("searchevents.php")
            .appendQueryParameter("e", league)
            .build()
            .toString()
    }
}