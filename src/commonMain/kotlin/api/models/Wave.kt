package api.models

import kotlinx.serialization.Serializable

/**
 *
 *
 * @param title
 * @param animationUrl
 */
@Serializable
data class Wave(
    val title: String,
    val animationUrl: String
)