package id.scode.thesportsdb.data.network

import java.net.URL

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 03 11/3/19 3:03 AM 2019
 * id.scode.thesportsdb.data.network
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

// network service for get data from server
class ApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}
