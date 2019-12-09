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

// Access Property For Context
val Context.databaseTeams: TeamDatabaseOpenHelper
    get() = TeamDatabaseOpenHelper.getInstance(applicationContext)

val Context.databaseEventPrevMatch: EventPrevMatchDbOpenHelper
    get() = EventPrevMatchDbOpenHelper.getInstance(applicationContext)

val Context.databaseEventNextMatch: EventNextMatchDbOpenHelper
    get() = EventNextMatchDbOpenHelper.getInstance(applicationContext)
