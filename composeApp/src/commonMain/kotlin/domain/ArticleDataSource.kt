package domain

import data.local.Topic
import kotlinx.coroutines.flow.Flow
import org.example.momandbaby2.ArticleEntity


fun ArticleEntity.toArticle(): Topic{
    return Topic(
        title = title,
        imageURL = imageURL,
        subTopics = null,
        contents = contents
    )
}
interface ArticleDataSource{
    fun getSpecificArticles(text:String): Flow<List<Topic>>
    suspend fun insertArticles(article: Topic)
    suspend fun deleteAllArticles()
    fun getAllArticles():Flow<List<Topic>>
}