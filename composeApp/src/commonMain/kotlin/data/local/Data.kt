package data.local

import kotlinx.serialization.Serializable

@Serializable
data class MainData(
    val topics: List<Topic>
)