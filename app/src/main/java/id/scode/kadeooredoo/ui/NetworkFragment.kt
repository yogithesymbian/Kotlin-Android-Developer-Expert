package id.scode.kadeooredoo.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import id.scode.kadeooredoo.data.db.network.DownloadCallback
import id.scode.kadeooredoo.data.db.network.DownloadTask

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 7:14 AM 2019
 * id.scode.kadeooredoo.ui
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */

private const val TAG = "NetworkFragment"
private const val URL_KEY = "UrlKey"

class NetworkFragment : Fragment() {

    private var callback: DownloadCallback<String>? = null
    private var downloadTask: DownloadTask? = null
    private var urlString: String? = null

    companion object {
        /**
         * Static initializer for NetworkFragment that sets the URL of the host it will be
         * downloading from.
         */
        fun getInstance(fragmentManager: FragmentManager, url: String): NetworkFragment {
            // Recover NetworkFragment in case we are re-creating the Activity due to a config change.
            // This is necessary because NetworkFragment might have a task that began running before
            // the config change occurred and has not finished yet.
            // The NetworkFragment is recoverable because it calls setRetainInstance(true).
            var networkFragment = fragmentManager.findFragmentByTag(TAG) as? NetworkFragment
            if (networkFragment == null) {
                networkFragment = NetworkFragment()
                networkFragment.arguments = Bundle().apply {
                    putString(URL_KEY, url)
                }
                fragmentManager.beginTransaction()
                    .add(networkFragment, TAG)
                    .commit()
            }
            return networkFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retain this Fragment across configuration changes in the host Activity.
        retainInstance = true
        urlString = arguments?.getString(URL_KEY)

    }

    override fun onAttach(context: Context) {
        context.let { super.onAttach(it) }
        // Host Activity will handle callbacks from task.
        callback = context as? DownloadCallback<String>
    }

    override fun onDetach() {
        super.onDetach()
        // Clear reference to host Activity to avoid memory leak.
        callback = null
    }

    override fun onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload()
        super.onDestroy()
    }

    /**
     * Start non-blocking execution of DownloadTask.
     */
    fun startDownload() {
        cancelDownload()
        callback?.also {
            downloadTask = DownloadTask(it).apply {
                execute(urlString)
            }
        }
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
     */
    fun cancelDownload() {
        downloadTask?.cancel(true)
    }

}
