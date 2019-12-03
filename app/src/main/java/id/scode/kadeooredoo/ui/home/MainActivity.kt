package id.scode.kadeooredoo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.scode.kadeooredoo.Item
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG_LOG = MainActivity::class.java.simpleName
        private var items: MutableList<Item> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG_LOG,"onCreate Run")

        initData()

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = RecyclerViewAdapter(
            this,
            items
        ){
            toast("${it.name}")
        }

    }

    private fun initData(){

        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)

        items.clear()

        for (i in name.indices) {
            items.add(
                Item(
                    name[i],
                    image.getResourceId(i, 0)
                )
            )
        }

        image.recycle()
    }

}
