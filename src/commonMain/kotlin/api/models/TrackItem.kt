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

import api.models.Track

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param id 
 * @param playCount 
 * @param recent 
 * @param timestamp 
 * @param track 
 */
@Serializable
data class TrackItem (

    @SerialName(value = "id") val id: kotlin.Double? = null,

    @SerialName(value = "playCount") val playCount: kotlin.Double? = null,

    @SerialName(value = "recent") val recent: kotlin.Boolean? = null,

    @SerialName(value = "timestamp") val timestamp: kotlin.String? = null,

    @SerialName(value = "track") val track: Track? = null

)

