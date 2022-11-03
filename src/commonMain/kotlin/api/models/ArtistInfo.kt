package api.models

import kotlinx.serialization.Serializable

/**
 *
 *
 * @param artist
 * @param albums
 * @param alsoAlbums
 * @param popularTracks
 * @param similarArtists
 * @param allCovers
 * @param concerts
 * @param videos
 * @param clips
 * @param vinyls
 * @param hasPromotions
 * @param lastReleases
 * @param stats
 * @param customWave
 * @param playlists
 */
@Serializable
data class ArtistInfo(
    val artist: Artist,
    val albums: List<Album>,
    val alsoAlbums: List<Album>,
    val popularTracks: List<Track>,
    val similarArtists: List<Artist>,
    val allCovers: List<Cover>,
    val concerts: List<Concert>,
    val videos: List<Video>,
    val clips: List<Video>,
    val vinyls: List<Album>,
    val hasPromotions: Boolean,
    val lastReleases: List<Album>,
    val stats: Statistics? = null,
    val customWave: Wave? = null,
    val playlists: List<Playlist>? = null
) {
    /**
     *
     *
     * @param lastMonthListeners
     */
    @Serializable
    data class Statistics(
        val lastMonthListeners: Long
    )
}