package id.scode.kadeooredoo.data.db.entities

import com.google.gson.annotations.SerializedName

data class Table(
    val draw: String? = null,
    val goalsagainst: String? = null,
    val goalsdifference: String? = null,
    val goalsfor: String? = null,
    val loss: String? = null,
    val name: String? = null,
    val played: String? = null,

    @SerializedName("teamid")
    val teamId: String? = null,

    val total: String? = null,
    val win: String? = null
)