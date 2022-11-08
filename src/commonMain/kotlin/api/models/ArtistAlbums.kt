package api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArtistAlbums(
    val albums: List<Album>
)