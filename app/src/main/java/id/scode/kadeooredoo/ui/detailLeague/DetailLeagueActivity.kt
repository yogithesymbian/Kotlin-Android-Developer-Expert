package id.scode.kadeooredoo.ui.detailLeague

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.ui.home.MainActivity.Companion.DETAIL_LEAGUE

class DetailLeagueActivity : AppCompatActivity() {

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    private val appBarConfiguration = AppBarConfiguration(
        setOf(
             R.id.navigation_dashboard, R.id.navigation_previous, R.id.navigation_next
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val idLeague = intent.getStringExtra(DETAIL_LEAGUE)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""
        // setupActionBarWithNavController(navController, appBarConfiguration) //bug error
        navView.setupWithNavController(navController)

        val bundle = bundleOf("ID_LEAGUE" to idLeague)

        navController.navigate(R.id.navigation_dashboard, bundle)
        val orderNav = navController.graph.findNode(R.id.navigation_dashboard)
        val orderNavPrev = navController.graph.findNode(R.id.navigation_previous)
        val orderNavNext = navController.graph.findNode(R.id.navigation_next)
        orderNav?.addArgument(
            "ID_LEAGUE", NavArgument.Builder()
            .setType(NavType.StringType)
            .setDefaultValue(idLeague)
            .build()
        )

        orderNavPrev?.addArgument(
            "ID_LEAGUE", NavArgument.Builder()
                .setType(NavType.StringType)
                .setDefaultValue(idLeague)
                .build()
        )

        orderNavNext?.addArgument(
            "ID_LEAGUE", NavArgument.Builder()
                .setType(NavType.StringType)
                .setDefaultValue(idLeague)
                .build()
        )


        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.navigation_previous -> {
                    Log.d(TAG_LOG, "you are in dashboard league $idLeague")


                }
                R.id.navigation_dashboard -> {
                    Log.d(TAG_LOG, "you are in next league")

                }
                R.id.navigation_next -> {
                    Log.d(TAG_LOG, "you are in previous league")

                }
                else -> Log.d(TAG_LOG, "you are in else")
            }
        }



    }
    // https://codelabs.developers.google.com/codelabs/android-navigation/index.html?index=..%2F..index#8
    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)

    companion object{
        private val TAG_LOG = DetailLeagueActivity::class.java.simpleName
    }
}
