package id.scode.kadeooredoo.data.db.network.responses

import com.google.gson.annotations.SerializedName
import id.scode.kadeooredoo.data.db.entities.EventPrevious

data class PreviousLeagueResponse(
    @SerializedName("events")
    val eventPrevious: List<EventPrevious>
)