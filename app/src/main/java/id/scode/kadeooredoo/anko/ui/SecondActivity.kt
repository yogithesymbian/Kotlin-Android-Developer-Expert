package id.scode.kadeooredoo.anko.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*

/**
 * @Authors scode
 * Created on 31 10/31/19 7:15 AM 2019
 * idClubFootball.scode.kadeooredoo.anko.ui
 * East Borneo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class SecondActivity : AppCompatActivity(){


    private var name: String = ""
    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        SecondActivityUI().setContentView(this)
        verticalLayout {
            padding = dip(16)
            nameTextView = textView()
        }
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        intent.also {
            name = it.getStringExtra("name")
        }
        nameTextView.text = name


    }
}