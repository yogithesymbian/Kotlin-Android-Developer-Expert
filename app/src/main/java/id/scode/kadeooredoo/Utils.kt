package id.scode.kadeooredoo

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import id.scode.kadeooredoo.data.db.helper.EventNextMatchDbOpenHelper
import id.scode.kadeooredoo.data.db.helper.EventPrevMatchDbOpenHelper
import id.scode.kadeooredoo.data.db.helper.TeamDatabaseOpenHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 04 11/4/19 7:00 AM 2019
 * id.scode.kadeooredoo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

const val EN_LANG = "en_lang"
const val JP_LANG = "jp_lang"
const val SPORT = "Soccer"
const val EXCEPTION_NULL = "exception_null"

// ID_LEAGUE={idLeague} param
// in home | ui/home/HomeActivity.kt
const val SEARCH_ALL_TEAM = "search_all_teams.php" // TeamsFragment.kt // use
/**
 *  in detailleague | ui/detailleague/DetailLeagueActivity.kt | NavigationGraph
 * ----------------------------------------------------------------------------------------
 */
const val LOOKUP_LEAGUE = "lookupleague.php" // DashboardFragment.kt
const val EVENT_NEXT_LEAGUE = "eventsnextleague.php" // PreviousMatchLeagueFragment.kt
const val EVENT_PAST_LEAGUE = "eventspastleague.php" // NextMatchLeagueFragment.kt
const val SEARCH_EVENT =
    "searchevents.php" // PreviousMatchLeagueFragment.kt && NextMatchLeagueFragment.kt

// ID_EVENT={idEvent} param
// onClick recyclerView of event_next | event_past
const val LOOKUP_EVENT = "lookupevent.php" // DetailMatchLeagueActivity.kt
// ----------------------------------------------------------------------------------------

// ID_TEAM={idTeam} param
const val LOOKUP_TEAM =
    "lookupteam.php" // get badge logo team all activity and fragment who is load the img

// FINAL PROJECT RESOURCE ADDED
const val LOOKUP_TABLE = "lookuptable.php" // l=idLeague
const val SEARCH_TEAM = "searchteams.php" //t=query
// {idTeam}
const val EVENT_NEXT_TEAM = "eventsnext.php" //{idTeam}
const val EVENT_LAST_TEAM = "eventslast.php" //{idTeam} // Serialized LAST | PAST | -> to "PREV"

/**
 * value isTest = isForTesting
 * value isNotTest or null = isForDeploy
 */
const val TESTING_FLAG = "isTest"
const val TESTING_FLAG_MATCH = "isTest"
//const val TESTING_FLAG = "isNotTest"

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun toGMTFormat(date: String, time: String): Date? { // nrohman dicoding discuss
    val formatter = SimpleDateFormat("yy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("GMT+7")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}

@SuppressLint("SimpleDateFormat")
fun toSimpleString(date: Date?): String? = with(date ?: Date()) {
    SimpleDateFormat("EEE, dd MMM yyy").format(this)
}

// Access Property For Context
val Context.databaseTeams: TeamDatabaseOpenHelper
    get() = TeamDatabaseOpenHelper.getInstance(applicationContext)

val Context.databaseEventPrevMatch: EventPrevMatchDbOpenHelper
    get() = EventPrevMatchDbOpenHelper.getInstance(applicationContext)

val Context.databaseEventNextMatch: EventNextMatchDbOpenHelper
    get() = EventNextMatchDbOpenHelper.getInstance(applicationContext)
