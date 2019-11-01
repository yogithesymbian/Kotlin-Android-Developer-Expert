package id.scode.kadeooredoo.anko.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import id.scode.kadeooredoo.*
import org.jetbrains.anko.design.snackbar

/**
 * @Authors scode
 * Created on 30 10/30/19 11:57 PM 2019
 * idClubFootball.scode.kadeooredoo.anko.ui
 * East Borneo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }

    class MainActivityUI : AnkoComponent<MainActivity>, AnkoLogger{
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

            verticalLayout {
                padding = dip(16)

                // EditText
                val name = editText {
                    hint = "What's your name?"
                }
                // Button Show Toast
                button("Say Hello"){
                    backgroundColor = R.color.colorAccent
                    textColor = Color.WHITE
                    setOnClickListener {

                        toast("Hello, ${name.text}")

                        info("button say hello clicked")
                        debug(7)
                        error(null)

                    }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }
                // Button Show Alert
                button("Show Alert"){
                    backgroundColor = R.color.colorAccent
                    textColor = Color.WHITE
                    setOnClickListener {
                        alert(
                        "Add to Favorite ?",
                        "Favorite ${name.text}!"
                    ){
                            yesButton { toast("ya....") }
                            noButton {  }
                        }.show()
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }

                // Button Show Selector
                button("Show Selector"){
                    backgroundColor = R.color.colorAccent
                    textColor = Color.WHITE
                    setOnClickListener {
                        val club = listOf(
                            "Barcelona",
                            "Real Madrid",
                            "Liverpool"
                        )
                        selector(
                            "Hello, ${name.text}! What's footbal club do you hate?",
                            club
                        ){_,i ->
                            toast("So you love ${club[i]}, right?")
                        }
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }
                // Button Show Snackbar
                button("Show Snackbar"){
                    backgroundColor = R.color.colorAccent
                    textColor = Color.WHITE

                    setOnClickListener {
                        Snackbar.make(it, "Snackbar", Snackbar.LENGTH_SHORT).show()
                        it.snackbar("sandbar")
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }

                // Button Go To Second Activity
                button("Go To SecondActivity"){
                    backgroundColor = R.color.colorAccent
                    textColor = Color.WHITE

                    setOnClickListener {
                        startActivity<SecondActivity>("name" to "${name.text}")
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(5)
                }
            }
        }
    }
}

