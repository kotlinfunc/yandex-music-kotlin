package api.models

import kotlinx.serialization.Serializable

@Serializable
data class TrackSupplement(
    val id: Long,
    val lyrics: Lyrics? = null,
    val videos: List<Video>? = null
)
