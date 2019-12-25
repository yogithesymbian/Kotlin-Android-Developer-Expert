package id.scode.kadeooredoo.data.db.entities

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 08 12/8/19 3:48 PM 2019
 * id.scode.kadeooredoo.data.db.entities
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

data class Favorite(
    val id: Long?,
    val teamId: String?,
    val teamName: String?,
    val teamBadge: String?,
    val teamDescEn: String?,
    val teamDescJp: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_DESC_EN: String = "TEAM_DESC_EN"
        const val TEAM_DESC_JP: String = "TEAM_DESC_JP"
    }
}