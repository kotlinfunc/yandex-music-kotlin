package api.models

import kotlinx.serialization.Serializable

@Serializable
data class Ids<T>(val ids: List<T>)