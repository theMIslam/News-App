package com.example.newsapp.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Article

@Database(
    entities = [Article::class],
    version = 3
)

@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile
        private var articleDbInstance: ArticleDataBase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = articleDbInstance ?: synchronized(LOCK) {
            articleDbInstance ?: createDatabaseInstance(context).also {
                articleDbInstance = it
            }
        }

        private fun createDatabaseInstance(context: Context) =
        Room.databaseBuilder(
        context, ArticleDataBase::class.java,
        "articles.db.db"
        ).fallbackToDestructiveMigration().build()
    }
}