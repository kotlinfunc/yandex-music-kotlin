package api.models

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

    val durationMs: Int,

    val explicit: Boolean,

    val fileSize: Int,

    val id: Long,

    val lyricsAvailable: Boolean,

    val major: Label? = null,

    val ogImage: String,

    val previewDurationMs: Int,

    val r128: R128,

    val realId: String,

    val regions: List<String>,

    val title: String,
)
