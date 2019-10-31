package id.scode.kadeooredoo.submission.data.db.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 3:02 PM 2019
 * id.scode.kadeooredoo.submission.data.db.pojo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
@Parcelize
data class ItemClubFootball(
    val id: Int?,
    val nameClubFootball: String?,
    val descClubFootball: String?,
    val imageClubFootball: Int
) : Parcelable