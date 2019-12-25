package id.scode.kadeooredoo.ui.home.view

import id.scode.kadeooredoo.data.db.entities.Team

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 04 11/4/19 7:08 AM 2019
 * id.scode.kadeooredoo.ui.home
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
interface TeamsView {

    fun showLoading()
    fun hideLoading()

    fun showTeamList(data: List<Team>?)
    fun showTeamAwayList(data: List<Team>?, checkIdTeam: String)
    fun exceptionNullObject(msg: String)

}