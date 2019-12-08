package id.scode.kadeooredoo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.scode.kadeooredoo.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btm_teams -> loadTeamsFragment(savedInstanceState)
                R.id.btm_fav -> loadFavoriteTeamsFragment(savedInstanceState)
            }
            true
        }

        bottom_navigation.selectedItemId = R.id.btm_teams
    }

    private fun loadFavoriteTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoriteTeamsFragment(),
                    FavoriteTeamsFragment::class.java.simpleName
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