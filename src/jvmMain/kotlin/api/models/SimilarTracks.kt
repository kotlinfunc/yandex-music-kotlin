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

package org.openapitools.client.models

import org.openapitools.client.models.Track

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * список похожих треков на другой трек
 *
 * @param track 
 * @param similarTracks Похожие треки
 */
@Serializable
data class SimilarTracks (

    @SerialName(value = "track") val track: Track? = null,

    /* Похожие треки */
    @SerialName(value = "similarTracks") val similarTracks: kotlin.collections.List<Track>? = null

)

