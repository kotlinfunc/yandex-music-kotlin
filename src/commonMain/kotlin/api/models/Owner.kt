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
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param login 
 * @param name 
 * @param sex 
 * @param uid 
 * @param verified 
 */
@Serializable
data class Owner (

    @SerialName(value = "login") val login: String,

    @SerialName(value = "name") val name: String,

    @SerialName(value = "sex") val sex: String,

    @SerialName(value = "uid") val uid: Long,

    @SerialName(value = "verified") val verified: Boolean

)

