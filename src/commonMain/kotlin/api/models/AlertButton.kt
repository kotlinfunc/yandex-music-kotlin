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
 * кнопка со ссылкой
 *
 * @param text Текст на кнопке
 * @param bgColor Цвет заднего фона (HTML)
 * @param textColor Цвет текста (HTML)
 * @param uri Ссылка, куда ведет кнопка
 */
@Serializable
data class AlertButton (

    /* Текст на кнопке */
    @SerialName(value = "text") val text: kotlin.String? = null,

    /* Цвет заднего фона (HTML) */
    @SerialName(value = "bgColor") val bgColor: kotlin.String? = null,

    /* Цвет текста (HTML) */
    @SerialName(value = "textColor") val textColor: kotlin.String? = null,

    /* Ссылка, куда ведет кнопка */
    @SerialName(value = "uri") val uri: kotlin.String? = null

)

