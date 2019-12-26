package id.scode.kadeooredoo.ui.classificationmatch.view

import id.scode.kadeooredoo.data.db.entities.Table

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 25 12/25/19 9:43 PM 2019
 * id.scode.kadeooredoo.ui.classificationmatch.view
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */

interface ClassificationMatchView {

    fun showLoading()
    fun hideLoading()
    fun showClassificationMatchTable(data: List<Table>?)

}