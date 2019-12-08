package id.scode.kadeooredoo.data.db.entities

data class EventDetailMatch(
    val dateEvent: String ?= null,
    val dateEventLocal: String ?= null,
    val idAwayTeam: String ?= null,
    val idEvent: String ?= null,
    val idHomeTeam: String ?= null,
    val idLeague: String ?= null,
    val idSoccerXML: String ?= null,
    val intAwayScore: String ?= null,
    val intAwayShots: String ?= null,
    val intHomeScore: String ?= null,
    val intHomeShots: String ?= null,
    val intRound: String ?= null,
    val intSpectators: String ?= null,
    val strAwayFormation: String ?= null,
    val strAwayGoalDetails: String ?= null,
    val strAwayLineupDefense: String ?= null,
    val strAwayLineupForward: String ?= null,
    val strAwayLineupGoalkeeper: String ?= null,
    val strAwayLineupMidfield: String ?= null,
    val strAwayLineupSubstitutes: String ?= null,
    val strAwayRedCards: String ?= null,
    val strAwayTeam: String ?= null,
    val strAwayYellowCards: String ?= null,
    val strBanner: String ?= null,
    val strCircuit: String ?= null,
    val strCity: String ?= null,
    val strCountry: String ?= null,
    val strDate: String ?= null,
    val strDescriptionEN: String ?= null,
    val strEvent: String ?= null,
    val strEventAlternate: String ?= null,
    val strFanart: String ?= null,
    val strFilename: String ?= null,
    val strHomeFormation: String ?= null,
    val strHomeGoalDetails: String ?= null,
    val strHomeLineupDefense: String ?= null,
    val strHomeLineupForward: String ?= null,
    val strHomeLineupGoalkeeper: String ?= null,
    val strHomeLineupMidfield: String ?= null,
    val strHomeLineupSubstitutes: String ?= null,
    val strHomeRedCards: String ?= null,
    val strHomeTeam: String ?= null,
    val strHomeYellowCards: String ?= null,
    val strLeague: String ?= null,
    val strLocked: String ?= null,
    val strMap: String ?= null,
    val strPoster: String ?= null,
    val strResult: String ?= null,
    val strSeason: String ?= null,
    val strSport: String ?= null,
    val strTVStation: String ?= null,
    val strThumb: String ?= null,
    val strTime: String ?= null,
    val strTimeLocal: String ?= null,
    val strTweet1: String ?= null,
    val strTweet2: String ?= null,
    val strTweet3: String ?= null,
    val strVideo: String ?= null
) {
    companion object{
        const val EVENT = "EVENT"
        const val SEASON = "SEASON"

        const val HOME_TEAM = "HOME_TEAM"
        const val HOME_SCORE = "HOME_SCORE"

        const val AWAY_TEAM = "AWAY_TEAM"
        const val AWAY_SCORE = "ASCR"

//        dateEvent = it.dateEvent,
//        strTime = it.strTime,
//        strLocked = it.strLocked,
//
//        strHomeFormation = it.strHomeFormation,
//        strAwayFormation = it.strAwayFormation,
//
//        strHomeGoalDetails = it.strHomeGoalDetails,
//        strAwayGoalDetails = it.strAwayGoalDetails
    }
}