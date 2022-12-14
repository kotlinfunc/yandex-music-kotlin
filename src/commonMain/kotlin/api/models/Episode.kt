package api.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val artists: List<Artist>,
    val albums: List<Podcast>,

    val available: Boolean,
    val availableForPremiumUsers: Boolean,
    val availableFullWithoutPermission: Boolean,

    val coverUri: String,

    val description: String? = null,

    val durationMs: Int,

    val explicit: Boolean? = null,

    val fileSize: Int,

    val id: Long,

    val lyricsAvailable: Boolean,

    val major: Label? = null,

    val ogImage: String,

    val previewDurationMs: Int,

    val pubDate: LocalDate? = null,

    val r128: R128? = null,

    val realId: String,

    val regions: List<String>? = null,

    val shortDescription: String? = null,

    val title: String,
)
