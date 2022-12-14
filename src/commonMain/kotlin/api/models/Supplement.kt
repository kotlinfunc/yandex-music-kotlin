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

import kotlinx.serialization.*

/**
 * дополнительная информация о треке
 *
 * @param id Уникальный идентификатор дополнительной информации.
 * @param lyrics 
 * @param videos 
 * @param radioIsAvailable Доступно ли радио.
 * @param description Полное описание эпизода подкаста.
 */
@Serializable
data class Supplement (

    /* Уникальный идентификатор дополнительной информации. */
    @SerialName(value = "id") val id: kotlin.Double? = null,

    @SerialName(value = "lyrics") val lyrics: Lyrics? = null,

    @SerialName(value = "videos") val videos: Video? = null,

    /* Доступно ли радио. */
    @SerialName(value = "radioIsAvailable") val radioIsAvailable: kotlin.Boolean? = null,

    /* Полное описание эпизода подкаста. */
    @SerialName(value = "description") val description: kotlin.String? = null

)

