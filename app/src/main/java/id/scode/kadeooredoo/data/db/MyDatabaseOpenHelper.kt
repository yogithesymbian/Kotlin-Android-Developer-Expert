package id.scode.kadeooredoo.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.scode.kadeooredoo.data.db.entities.Favorite
import org.jetbrains.anko.db.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 08 12/8/19 3:50 PM 2019
 * id.scode.kadeooredoo.data.db
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class MyDatabaseOpenHelper (context: Context): ManagedSQLiteOpenHelper(
    context,
    "FavoriteTeam.db",
    null,
    1
){
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // create tables
        db?.createTable(
            Favorite.TABLE_FAVORITE,
            true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.TEAM_ID to TEXT + UNIQUE,
            Favorite.TEAM_NAME to TEXT,
            Favorite.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // for upgrade tables, as usual
        db?.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

// Access Property For Context
val Context.database : MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)