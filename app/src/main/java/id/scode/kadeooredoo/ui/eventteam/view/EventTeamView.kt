package id.scode.kadeooredoo.ui.eventteam.view

import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 6:05 AM 2019
 * id.scode.kadeooredoo.ui.eventteam.view
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
interface EventTeamView {
    fun showLoading()
    fun hideLoading()

    fun showEventTeamPrev(data: List<EventPrevious>)
    fun showEventTeamNext(data: List<EventNext>)
}