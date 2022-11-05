package api.models

import kotlinx.serialization.Serializable

@Serializable
data class R128(
    val i: Double,
    val tp: Double
)
