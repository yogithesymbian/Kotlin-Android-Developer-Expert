package id.scode.kadeooredoo.ui.detailLeague.view

import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.entities.EventSearch

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 05 12/5/19 11:43 AM 2019
 * id.scode.kadeooredoo.ui.detailLeague.view
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

interface PreviousMatchLeagueView {

    fun showLoading()
    fun hideLoading()

    fun showPreviousLeague(data: List<EventPrevious>?)
    fun showSearchPreviousLeague(data: List<EventSearch>?)
}