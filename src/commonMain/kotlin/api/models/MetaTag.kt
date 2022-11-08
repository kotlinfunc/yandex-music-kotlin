package api.models

import kotlinx.serialization.Serializable

@Serializable
data class MetaTag(
    val id: String,
    val coverUri: String? = null,
    val color: String,
    val title: Title,
    val stationId: String,

    val artists: List<Artist>? = null,
    val albums: List<Album>? = null,
    val playlists: List<Playlist>? = null,

    val artistsSortByValues: List<SortedBy>? = null,
    val albumsSortByValues: List<SortedBy>? = null,
    val playlistsSortByValues: List<SortedBy>? = null,
    val sortByValues: List<SortedBy>? = null
) {
    @Serializable
    data class SortedBy(val value: String, val title: String, val active: Boolean)
}
