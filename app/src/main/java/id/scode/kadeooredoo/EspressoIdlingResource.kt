//package id.scode.kadeooredoo
//
//import androidx.test.espresso.IdlingResource
//import androidx.test.espresso.idling.CountingIdlingResource
//
///**
// * @Authors scode | Yogi Arif Widodo
// * Created on 11 12/11/19 7:01 AM 2019
// * id.scode.kadeooredoo
// * https://github.com/yogithesymbian
// * Android Studio 3.5.1
// * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
// * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
// * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
// * Linux 5.2.0-kali3-amd64
// */
//object EspressoIdlingResource {
//    private val RESOURCE = "GLOBAL"
//    private val countingIdlingResource = CountingIdlingResource(RESOURCE)
//
//    val idlingresource: IdlingResource
//        get() = countingIdlingResource
//
//    fun increment() {
//        countingIdlingResource.increment()
//    }
//
//    fun decrement() {
//        countingIdlingResource.decrement()
//    }
//}