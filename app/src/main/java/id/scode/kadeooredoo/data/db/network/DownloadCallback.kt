package id.scode.kadeooredoo.data.db.network

import android.net.NetworkInfo

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 7:08 AM 2019
 * id.scode.kadeooredoo.data.db.network
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
const val ERROR = -1
const val CONNECT_SUCCESS = 0
const val GET_INPUT_STREAM_SUCCESS = 1
const val PROCESS_INPUT_STREAM_IN_PROGRESS = 2
const val PROCESS_INPUT_STREAM_SUCCESS = 3

interface DownloadCallback<T> {

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    fun updateFromDownload(result: T?)

    /**
     * Get the device's active network status in the form of a NetworkInfo object.
     */
    @Suppress("DEPRECATION")
    fun getActiveNetworkInfo(): NetworkInfo

    /**
     * Indicate to callback handler any progress update.
     * @param progressCode must be one of the constants defined in DownloadCallback.Progress.
     * @param percentComplete must be 0-100.
     */
    fun onProgressUpdate(progressCode: Int, percentComplete: Int)

    /**
     * Indicates that the download operation has finished. This method is called even if the
     * download hasn't completed successfully.
     */
    fun finishDownloading()
}
