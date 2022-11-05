package api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    val id: String,
    val title: String,
    val chartDescription: String,
    val chart: Playlist
)

@Serializable
enum class ChartScope(val value: String) {
    @SerialName("russia") RUSSIA("russia"),
    @SerialName("world") WORLD("world")
}
