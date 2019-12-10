package id.scode.kadeooredoo.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import id.scode.kadeooredoo.ui.home.subfavorite.eventnext.FavNextFragment
import id.scode.kadeooredoo.ui.home.subfavorite.eventprev.FavPrevFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 09 12/9/19 9:47 AM 2019
 * id.scode.kadeooredoo.ui.home.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class FavoriteEventSectionPagerAdapter(
    fragmentManager: FragmentManager,
    private val tabs: TabLayout
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), AnkoLogger {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                info("pos 1")
                FavPrevFragment()
            }
            1 -> {
                info("pos 2")
                FavNextFragment()
            }
            else -> {
                info("pos else")
                FavPrevFragment()
            }
        }
    }

    override fun getCount(): Int = tabs.tabCount

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "1 EVENT PREVIOUS LEAGUE"
            1 -> return "2 EVENT NEXT LEAGUE"
        }
        return null
    }


}