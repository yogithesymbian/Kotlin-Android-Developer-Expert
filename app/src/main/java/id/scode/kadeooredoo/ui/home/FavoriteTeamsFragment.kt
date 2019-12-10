package id.scode.kadeooredoo.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Favorite
import id.scode.kadeooredoo.databaseTeams
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY_FAV_TEAM
import id.scode.kadeooredoo.ui.home.adapter.FavoriteTeamsAdapter
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamsFragment : Fragment(), AnkoLogger {

    private var favoritesMutableList: MutableList<Favorite> = mutableListOf()
    private lateinit var favoriteTeamsAdapter: FavoriteTeamsAdapter
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteTeamsAdapter = FavoriteTeamsAdapter(favoritesMutableList) {
            context?.startActivity<TeamsDetailActivity>(DETAIL_KEY_FAV_TEAM to "${it.teamId}")
        }

        recyclerView.adapter = favoriteTeamsAdapter

        swipeRefreshLayout.onRefresh {
            info("onRefresh showFav")
            showFavorite()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favorite_main, container, false)

        recyclerView = root.findViewById(R.id.rv_teams)
        imageView = root.findViewById(R.id.img_exception_favorite)
        swipeRefreshLayout = root.findViewById(R.id.swrl_teams)


        // set the layout
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()

        return root
    }


    // show data Anko SQLite
    private fun showFavorite() {
        // clear before assign
        favoritesMutableList.clear()

        context?.applicationContext?.databaseTeams?.use {
            // stop refresh if do
            swipeRefreshLayout.isRefreshing = false

            // query anko SQLite select and parser the result
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())

            // assign all data to favorite mutableList
            favoritesMutableList.addAll(favorite)

            // check data
            if (favoritesMutableList.isNullOrEmpty()) {
                swipeRefreshLayout.gone()
                imageView.visible()
            } else {
                info("$ showing favorite ${favoritesMutableList[0].teamName}")
                swipeRefreshLayout.visible()
                imageView.gone()
            }

            // getNot-if the adapter if change
            favoriteTeamsAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        info("onResume")
        showFavorite()

    }

}
