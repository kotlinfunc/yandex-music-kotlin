package api.models

import kotlinx.serialization.Serializable

@Serializable
data class Description(val text: String, val uri: String)
