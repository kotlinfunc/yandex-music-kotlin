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
 * 
 *
 * @param total Количество результатов
 * @param perPage Максимальное количество результатов на странице.
 * @param order Позиция блока
 * @param results 
 */
@Serializable
data class PagingResult<T> (

    /* Количество результатов */
    @SerialName(value = "total") val total: Int,

    /* Максимальное количество результатов на странице. */
    @SerialName(value = "perPage") val perPage: Int,

    /* Позиция блока */
    @SerialName(value = "order") val order: Int,

    @SerialName(value = "results") val results: List<T> = emptyList()

)
