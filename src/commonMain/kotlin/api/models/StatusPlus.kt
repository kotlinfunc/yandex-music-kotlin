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
 * Информация о Plus подписке
 *
 * @param hasPlus 
 * @param isTutorialCompleted 
 */
@Serializable
data class StatusPlus (

    @SerialName(value = "hasPlus") val hasPlus: kotlin.Boolean? = null,

    @SerialName(value = "isTutorialCompleted") val isTutorialCompleted: kotlin.Boolean? = null

)

