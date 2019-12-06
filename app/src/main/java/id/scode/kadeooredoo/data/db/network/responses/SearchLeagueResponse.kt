package id.scode.kadeooredoo.data.db.network.responses

import com.google.gson.annotations.SerializedName
import id.scode.kadeooredoo.data.db.entities.EventSearch

data class SearchLeagueResponse(
    @SerializedName("event")
    val eventSearch: List<EventSearch>
)