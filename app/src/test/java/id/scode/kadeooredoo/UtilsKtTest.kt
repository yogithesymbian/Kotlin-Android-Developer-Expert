package id.scode.kadeooredoo

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 10 12/10/19 8:48 PM 2019
 * id.scode.kadeooredoo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class UtilsKtTest {

    @Test
    fun toSimpleString() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("12/10/2019")
        assertEquals("Tue, 10 Dec 2019", toSimpleString(date))
    }
}