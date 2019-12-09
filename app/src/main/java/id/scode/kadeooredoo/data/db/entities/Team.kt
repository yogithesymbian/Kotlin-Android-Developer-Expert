package id.scode.kadeooredoo.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 04 11/4/19 7:02 AM 2019
 * id.scode.kadeooredoo.data.db.entities
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
@Parcelize
data class Team(
    val idLeague:String? = null,
    val idSoccerXML: String? = null,

    @SerializedName("idTeam")
    var teamId: String? = null,

    val intFormedYear: String? = null,
    val intLoved: String? = null,
    val intStadiumCapacity: String? = null,
    val strAlternate: String? = null,
    val strCountry: String? = null,
    val strDescriptionCN: String? = null,
    val strDescriptionDE: String? = null,
    val strDescriptionEN: String? = null,
    val strDescriptionES: String? = null,
    val strDescriptionFR: String? = null,
    val strDescriptionHU: String? = null,
    val strDescriptionIL: String? = null,
    val strDescriptionIT: String? = null,
    val strDescriptionJP: String? = null,
    val strDescriptionNL: String? = null,
    val strDescriptionNO: String? = null,
    val strDescriptionPL: String? = null,
    val strDescriptionPT: String? = null,
    val strDescriptionRU: String? = null,
    val strDescriptionSE: String? = null,
    val strDivision: String? = null,
    val strFacebook: String? = null,
    val strGender: String? = null,
    val strInstagram: String? = null,
    val strKeywords: String? = null,
    val strLeague: String? = null,
    val strLocked: String? = null,
    val strManager: String? = null,
    val strRSS: String? = null,
    val strSport: String? = null,
    val strStadium: String? = null,
    val strStadiumDescription: String? = null,
    val strStadiumLocation: String? = null,
    val strStadiumThumb: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null,

    val strTeamBanner: String? = null,
    val strTeamFanart1: String? = null,
    val strTeamFanart2: String? = null,
    val strTeamFanart3: String? = null,
    val strTeamFanart4: String? = null,
    val strTeamJersey: String? = null,
    val strTeamLogo: String? = null,
    val strTeamShort: String? = null,
    val strTwitter: String? = null,
    val strWebsite: String? = null,
    val strYoutube: String? = null
) : Parcelable {

    companion object{
        const val TABLE_FAVORITE_PREV: String = "TABLE_FAVORITE_PREV"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_ID_AWAY: String = "TEAM_ID_AWAY"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_BADGE_AWAY: String = "TEAM_BADGE_AWAY"
    }

}