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

import api.models.Cover
import api.models.Track

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

    @SerialName(value = "composer") val composer: Boolean? = null,

    @SerialName(value = "cover") val cover: Cover? = null,

    @SerialName(value = "decomposed") val decomposed: List<String>? = null,

    @SerialName(value = "genres") val genres: List<String>? = null,

    @SerialName(value = "id") val id: Long,

    @SerialName(value = "name") val name: String? = null,

    @SerialName(value = "various") val various: Boolean? = null,

    @SerialName(value = "popularTracks") val popularTracks: List<Track>? = null,

    /* Имеются ли в продаже билеты на концерт */
    @SerialName(value = "ticketsAvailable") val ticketsAvailable: Boolean? = null,

    @SerialName(value = "regions") val regions: List<String>? = null

)

