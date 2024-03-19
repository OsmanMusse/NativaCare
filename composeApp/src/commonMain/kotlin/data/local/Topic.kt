package data.local

import kotlinx.serialization.Serializable


@Serializable
data class Topic(
    val title: String,
    val imageURL: String,
    val subTopics: List<Topic>? = null,
    val contents: String? = null
)