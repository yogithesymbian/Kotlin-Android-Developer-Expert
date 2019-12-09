package id.scode.kadeooredoo.data.db.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 09 12/9/19 5:56 PM 2019
 * id.scode.kadeooredoo.data.db.entities
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
@Parcelize
data class FavTeamJoinDetail(

    val id: Long?,
    val teamId: String?,
    val teamIdAway: String?,
    val teamBadge: String?,
    val teamBadgeAway: String?,

    val eventId: String?,
    val event: String?,
    val season: String?,

    val homeTeam: String?,
    val homeScore: String?,

    val awayTeam: String?,
    val awayScore: String?,

    val dateEvent: String?,
    val timeEvent: String?,
    val locked: String?,
    val sportStr: String?,

    val homeFormation: String?,
    val awayFormation: String?,

    val homeGoalsDetail: String?,
    val awayGoalsDetail: String?,

    val hmShot: String?,
    val awShot: String?,

    val hmRedCard: String?,
    val awRedCard: String?,

    val hmYlCard: String?,
    val awYlCard: String?,

    val hmGkLine: String?,
    val awGkLine: String?,

    val hmDefLine: String?,
    val awDefLine: String?,

    val hmMidLine: String?,
    val awMidLine: String?,

    val hmFwLine: String?,
    val awFwLine: String?,

    val hmSubstLine: String?,
    val awSubstLine: String?,

    val linkTw: String?
) : Parcelable