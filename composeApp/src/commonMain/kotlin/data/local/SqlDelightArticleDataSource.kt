package data.local

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import domain.ArticleDataSource
import domain.toArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.nativacare.Database

class SqlDelightArticleDataSource(
    db: Database
): ArticleDataSource {

    private val queries = db.articleQueries

    override fun getSpecificArticles(text: String): Flow<List<Topic>> {
        return queries.getSpecificArticles(text).asFlow().mapToList(Dispatchers.Default).map { it.map { it.toArticle() } }
    }

    override suspend fun insertArticles(article: Topic) {
        queries.insertArticles(
            id = null,
            title = article.title,
            imageURL = article.imageURL,
            contents = article.contents ?: ""
        )
    }

    override suspend fun deleteAllArticles() {
        queries.deleteAllArticles()
    }

    override fun getAllArticles(): Flow<List<Topic>> {
        return queries.getAllArticles()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map {
                it.map {
                    it.toArticle()
                }
            }
    }
}