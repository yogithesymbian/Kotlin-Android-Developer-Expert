package id.scode.kadeooredoo.data.db.network

import android.net.ConnectivityManager
import android.os.AsyncTask
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection


/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 7:17 AM 2019
 * id.scode.kadeooredoo.data.db.network
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
/**
 * Implementation of AsyncTask designed to fetch data from the network.
 */
class DownloadTask(callback: DownloadCallback<String>)
    : AsyncTask<String, Int, DownloadTask.Result>() {

    private var callback: DownloadCallback<String>? = null

    init {
        setCallback(callback)
    }

    internal fun setCallback(callback: DownloadCallback<String>) {
        this.callback = callback
    }

    /**
     * Wrapper class that serves as a union of a result value and an exception. When the download
     * task has completed, either the result value or exception can be a non-null value.
     * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
     */
    class Result {
        var resultValue: String? = null
        var exception: Exception? = null

        constructor(resultValue: String) {
            this.resultValue = resultValue
        }

        constructor(exception: Exception) {
            this.exception = exception
        }
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    override fun onPreExecute() {
        if (callback != null) {
            val networkInfo = callback?.getActiveNetworkInfo()
            if (networkInfo?.isConnected == false
                || networkInfo?.type != ConnectivityManager.TYPE_WIFI
                && networkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                // If no connectivity, cancel task and update Callback with null data.
                callback?.updateFromDownload(null)
                cancel(true)
            }
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    override fun doInBackground(vararg urls: String): DownloadTask.Result? {
        var result: Result? = null
        if (!isCancelled && urls.isNotEmpty()) {
            val urlString = urls[0]
            result = try {
                val url = URL(urlString)
                val resultString = downloadUrl(url)
                if (resultString != null) {
                    Result(resultString)
                } else {
                    throw IOException("No response received.")
                }
            } catch (e: Exception) {
                Result(e)
            }

        }
        return result
    }

    /**
     * Updates the DownloadCallback with the result.
     */
    override fun onPostExecute(result: Result?) {
        callback?.apply {
            result?.exception?.also { exception ->
                updateFromDownload(exception.message)
                return
            }
            result?.resultValue?.also { resultValue ->
                updateFromDownload(resultValue)
                return
            }
            finishDownloading()
        }
    }

    /**
     * Override to add special behavior for cancelled AsyncTask.
     */
    override fun onCancelled(result: Result) {}


    @Throws(IOException::class)
    private fun downloadUrl(url: URL): String? {
        var connection: HttpsURLConnection? = null
        return try {
            connection = (url.openConnection() as? HttpsURLConnection)
            connection?.run {
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                readTimeout = 3000
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connectTimeout = 3000
                // For this use case, set HTTP method to GET.
                requestMethod = "GET"
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                doInput = true
                // Open communications link (network traffic occurs here).
                connect()
                publishProgress(CONNECT_SUCCESS)
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw IOException("HTTP error code: $responseCode")
                }
                // Retrieve the response body as an InputStream.
                publishProgress(GET_INPUT_STREAM_SUCCESS, 0)
                inputStream?.let { stream ->
                    // Converts Stream to String with max length of 500.
                    readStream(stream, 500)
                }
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            connection?.inputStream?.close()
            connection?.disconnect()
        }
    }
    /**
     * Converts the contents of an InputStream to a String.
     */
    @Throws(IOException::class, UnsupportedEncodingException::class)
    fun readStream(stream: InputStream, maxReadSize: Int): String? {
        val reader: Reader? = InputStreamReader(stream, "UTF-8")
        val rawBuffer = CharArray(maxReadSize)
        val buffer = StringBuffer()
        var readSize: Int = reader?.read(rawBuffer) ?: -1
        var maxReadBytes = maxReadSize
        while (readSize != -1 && maxReadBytes > 0) {
            if (readSize > maxReadBytes) {
                readSize = maxReadBytes
            }
            buffer.append(rawBuffer, 0, readSize)
            maxReadBytes -= readSize
            readSize = reader?.read(rawBuffer) ?: -1
        }
        return buffer.toString()
    }


}
