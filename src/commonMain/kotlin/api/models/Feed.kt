package api.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val generatedPlaylists: List<Playlist>,
    val headlines: List<Event>,
    val today: LocalDate,
    val days: List<Day>
) {
    @Serializable
    data class Day(
        val day: LocalDate,
        val events: List<Event>,
        val tracksToPlay: List<Track>)

    @Serializable
    sealed class Event {
        abstract val id: String
    }

    @Serializable
    @SerialName("notification")
    data class Notification(override val id: String, val message: String): Event()

    @Serializable
    @SerialName("genre-top")
    data class GenreTop(
        override val id: String,
        val genre: String,
        val radioIsAvailable: Boolean,
        val tracks: List<Track>): Event()

    @Serializable
    @SerialName("new-albums-of-favorite-genre")
    data class NewAlbumsOfFavoriteGenre(
        override val id: String,
        val genre: String,
        val albums: List<Album>): Event()
}
