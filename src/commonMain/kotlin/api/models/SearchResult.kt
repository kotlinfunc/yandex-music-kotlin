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

import org.openapitools.client.models.BestResult

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param type Тип результата
 * @param total Количество результатов
 * @param perPage Максимальное количество результатов на странице.
 * @param order Позиция блока
 * @param results 
 */
@Serializable
data class SearchResult (

    /* Тип результата */
    @SerialName(value = "type") val type: kotlin.String? = null,

    /* Количество результатов */
    @SerialName(value = "total") val total: kotlin.Double? = null,

    /* Максимальное количество результатов на странице. */
    @SerialName(value = "perPage") val perPage: kotlin.Double? = null,

    /* Позиция блока */
    @SerialName(value = "order") val order: kotlin.Double? = null,

    @SerialName(value = "results") val results: kotlin.collections.List<BestResult>? = null

)
