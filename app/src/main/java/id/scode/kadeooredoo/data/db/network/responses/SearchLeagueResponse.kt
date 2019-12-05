package id.scode.kadeooredoo.data.db.network.responses

import id.scode.kadeooredoo.data.db.entities.EventSearch

data class SearchLeagueResponse(
    val eventSearch: List<EventSearch>
)