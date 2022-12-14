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
 * @param execDurationMillis 
 * @param hostname 
 * @param reqId 
 */
@Serializable
data class InvocationInfo (

    @SerialName(value = "exec-duration-millis") val execDurationMillis: Long? = null,

    @SerialName(value = "hostname") val hostname: String,

    @SerialName(value = "req-id") val reqId: String

)

