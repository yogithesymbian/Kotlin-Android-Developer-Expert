package id.scode.kadeooredoo.ui.home

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.home.adapter.FavoriteEventSectionPagerAdapter
import id.scode.kadeooredoo.visible
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HomeActivity : AppCompatActivity(), AnkoLogger {

    // tabs config
    private lateinit var favoriteEventSectionPagerAdapter: FavoriteEventSectionPagerAdapter // bug onResume of TeamsFavoriteFragment so i move the pager into here activity
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager_container_home)


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btm_teams -> {
                    loadTeamsFragment(savedInstanceState)
                    tabLayout.gone()
                    viewPager.gone()
                }
                R.id.btm_fav -> {
                    loadFavoriteTeamsFragment(savedInstanceState)
                    tabLayout.visible()
                    viewPager.visible()
                }
            }
            true
        }

        bottom_navigation.selectedItemId = R.id.btm_teams

        /**
         * TabLayout View Pager
         */
        favoriteEventSectionPagerAdapter = FavoriteEventSectionPagerAdapter(
            supportFragmentManager,
            tabLayout
        )

        // Set up the ViewPager with the sections adapter.
        viewPager.adapter = favoriteEventSectionPagerAdapter

        /**
         * listener view pager favorite main
         */
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // later's
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // later's
            }

            @SuppressLint("Recycle", "ResourceType")
            @TargetApi(Build.VERSION_CODES.LOLLIPOP) //lollipop for windowStatusBarColor :D
            override fun onPageSelected(position: Int) {
                // avoiding double bang operator
                val eventPrevTab = viewPager.adapter?.count?.minus(2)
                val eventNextTab = viewPager.adapter?.count?.minus(1)

                when (position) {
                    eventPrevTab -> {
                        info("eventPrev Tab Got Clicked")
                        tabs.invalidate()
                        val tabs = tabLayout.getTabAt(position)
                        tabs?.select()
                    }
                    eventNextTab -> {
                        info("eventNext Tab Got Clicked")
                        tabLayout.invalidate()
                        val tabs = tabLayout.getTabAt(position)
                        tabs?.select()
                    }
                }
            }
        })
        /**
         * End Of listener view pager home
         */


        /**
         * indicator select view tabLayout
         */
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        tabLayout.setupWithViewPager(ViewPager(this@HomeActivity))
    }

    private fun loadFavoriteTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    TeamsFavoriteFragment(),
                    TeamsFavoriteFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    TeamsFragment(),
                    TeamsFragment::class.java.simpleName
                )
                .commit()
        }
    }
}