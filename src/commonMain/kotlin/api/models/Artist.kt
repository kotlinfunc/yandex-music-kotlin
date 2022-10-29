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

import org.openapitools.client.models.Cover
import org.openapitools.client.models.Track

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param composer 
 * @param cover 
 * @param decomposed 
 * @param genres 
 * @param id 
 * @param name 
 * @param various 
 * @param popularTracks 
 * @param ticketsAvailable Имеются ли в продаже билеты на концерт
 * @param regions 
 */
@Serializable
data class Artist (

    @SerialName(value = "composer") val composer: kotlin.Boolean? = null,

    @SerialName(value = "cover") val cover: Cover? = null,

    @SerialName(value = "decomposed") val decomposed: kotlin.collections.List<kotlin.String>? = null,

    @SerialName(value = "genres") val genres: kotlin.collections.List<kotlin.String>? = null,

    @SerialName(value = "id") val id: kotlin.String? = null,

    @SerialName(value = "name") val name: kotlin.String? = null,

    @SerialName(value = "various") val various: kotlin.Boolean? = null,

    @SerialName(value = "popularTracks") val popularTracks: kotlin.collections.List<Track>? = null,

    /* Имеются ли в продаже билеты на концерт */
    @SerialName(value = "ticketsAvailable") val ticketsAvailable: kotlin.Boolean? = null,

    @SerialName(value = "regions") val regions: kotlin.collections.List<kotlin.String>? = null

)
