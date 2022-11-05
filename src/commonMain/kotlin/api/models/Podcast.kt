package api.models

import kotlinx.serialization.Serializable

@Serializable
data class Podcast(
    val available: Boolean,
    val availableForPremiumUsers: Boolean,
    val availableFullWithoutPermission: Boolean? = null,

    val coverUri: String,

    val id: Long,

    val likesCount: Int? = null,

    val ogImage: String,

    val recent: Boolean,

    val regions: List<String>? = null,

    val releaseDate: String? = null,

    val title: String,

    val tracksCount: Int? = null,

    val veryImportant: Boolean,

    val year: Int? = null
)