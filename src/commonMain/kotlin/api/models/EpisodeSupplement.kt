package api.models

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeSupplement(
    val id: Long,
    val description: String
)
