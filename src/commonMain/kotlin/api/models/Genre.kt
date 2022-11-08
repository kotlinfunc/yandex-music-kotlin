package api.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: String,
    val weight: Int,
    val composerTop: Boolean,
    val title: String,
    val fullTitle: String? = null,
    val titles: Map<String, Title>,
    val color: String? = null,
    val images: Map<String, String>,
    val radioIcon: RadioIcon? = null,
    val subGenres: List<Genre>? = null
)
