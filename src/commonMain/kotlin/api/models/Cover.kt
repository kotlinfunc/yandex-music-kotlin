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


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param custom 
 * @param dir 
 * @param type 
 * @param itemsUri 
 * @param uri 
 * @param version 
 * @param error If exists other properties is missing
 */
@Serializable
data class Cover (

    @SerialName(value = "custom") val custom: kotlin.Boolean? = null,

    @SerialName(value = "dir") val dir: kotlin.String? = null,

    @SerialName(value = "type") val type: Cover.Type? = null,

    @SerialName(value = "itemsUri") val itemsUri: kotlin.collections.List<kotlin.String>? = null,

    @SerialName(value = "uri") val uri: kotlin.String? = null,

    @SerialName(value = "version") val version: kotlin.String? = null,

    /* If exists other properties is missing */
    @SerialName(value = "error") val error: kotlin.String? = null

) {

    /**
     * 
     *
     * Values: pic,mosaic
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "pic") pic("pic"),
        @SerialName(value = "mosaic") mosaic("mosaic");
    }
}
