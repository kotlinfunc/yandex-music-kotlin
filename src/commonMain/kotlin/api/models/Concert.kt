package api.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Concert(
    val artist: Artist,
    val id: String,
    val concertTitle: String,
    val afishaUrl: String,
    val city: String,
    val place: String,
    val address: String,
    @SerialName("datetime") val dateTime: Instant,
    val coordinates: DoubleArray,
    val map: String,
    val mapUrl: String,
    val hash: String,
    val images: List<String>,
    val contentRating: String,
    @SerialName(value = "metro-stations") val metroStations: List<MetroStation>? = null
) {
    @Serializable
    data class MetroStation(
        val title: String,
        @SerialName(value = "line-color") val lineColor: String)
}