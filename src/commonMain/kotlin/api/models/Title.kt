package api.models

import kotlinx.serialization.Serializable

@Serializable
data class Title(val title: String, val fullTitle: String? = null)