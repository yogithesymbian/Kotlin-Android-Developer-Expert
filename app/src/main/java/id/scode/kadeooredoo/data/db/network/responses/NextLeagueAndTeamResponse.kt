package id.scode.kadeooredoo.data.db.network.responses

import com.google.gson.annotations.SerializedName
import id.scode.kadeooredoo.data.db.entities.EventNext

data class NextLeagueAndTeamResponse(
    @SerializedName("events")
    val eventNexts: List<EventNext>
)