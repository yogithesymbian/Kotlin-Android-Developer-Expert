package id.scode.kadeooredoo.data.db.network.responses

import com.google.gson.annotations.SerializedName
import id.scode.kadeooredoo.data.db.entities.EventDetailMatch

data class DetailMatchLeagueResponse(
    @SerializedName("events")
    val eventDetailMatches: List<EventDetailMatch>
)