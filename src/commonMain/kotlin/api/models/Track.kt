/**
 * Yandex Music Api
 *
 * Swagger документация для Yandex Music API.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package api.models

import api.models.Album
import api.models.AlbumLabelsInner
import api.models.Artist
import api.models.TrackNormalization

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param albums 
 * @param artists 
 * @param available 
 * @param availableForPremiumUsers 
 * @param availableFullWithoutPermission 
 * @param coverUri Cover uri template
 * @param durationMs 
 * @param fileSize 
 * @param id 
 * @param lyricsAvailable 
 * @param major 
 * @param normalization 
 * @param ogImage 
 * @param previewDurationMs 
 * @param realId 
 * @param rememberPosition 
 * @param storageDir 
 * @param title 
 * @param type 
 */
@Serializable
data class Track (

    @SerialName(value = "albums") val albums: List<Album>? = null,

    @SerialName(value = "artists") val artists: List<Artist>? = null,

    @SerialName(value = "available") val available: kotlin.Boolean? = null,

    @SerialName(value = "availableForPremiumUsers") val availableForPremiumUsers: kotlin.Boolean? = null,

    @SerialName(value = "availableFullWithoutPermission") val availableFullWithoutPermission: kotlin.Boolean? = null,

    /* Cover uri template */
    @SerialName(value = "coverUri") val coverUri: kotlin.String? = null,

    @SerialName(value = "durationMs") val durationMs: kotlin.Double? = null,

    @SerialName(value = "fileSize") val fileSize: kotlin.Double? = null,

    @SerialName(value = "id") val id: Long,

    @SerialName(value = "lyricsAvailable") val lyricsAvailable: kotlin.Boolean? = null,

    @SerialName(value = "major") val major: AlbumLabelsInner? = null,

    @SerialName(value = "normalization") val normalization: TrackNormalization? = null,

    @SerialName(value = "ogImage") val ogImage: kotlin.String? = null,

    @SerialName(value = "previewDurationMs") val previewDurationMs: kotlin.Double? = null,

    @SerialName(value = "realId") val realId: kotlin.String? = null,

    @SerialName(value = "rememberPosition") val rememberPosition: kotlin.Boolean? = null,

    @SerialName(value = "storageDir") val storageDir: kotlin.String? = null,

    @SerialName(value = "title") val title: String,

    @SerialName(value = "type") val type: kotlin.String? = null

)

