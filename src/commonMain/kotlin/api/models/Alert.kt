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

import api.models.AlertButton

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Блок с предупреждениями о конце подписки и подарках
 *
 * @param alertId Уникальный идентификатор
 * @param text Текст предупреждения
 * @param bgColor Цвет заднего фона (HTML)
 * @param textColor Цвет текста (HTML)
 * @param alertType Тип предупреждения
 * @param button 
 * @param closeButton Наличие кнопки \"Закрыть\"
 */
@Serializable
data class Alert (

    /* Уникальный идентификатор */
    @SerialName(value = "alertId") val alertId: kotlin.String? = null,

    /* Текст предупреждения */
    @SerialName(value = "text") val text: kotlin.String? = null,

    /* Цвет заднего фона (HTML) */
    @SerialName(value = "bgColor") val bgColor: kotlin.String? = null,

    /* Цвет текста (HTML) */
    @SerialName(value = "textColor") val textColor: kotlin.String? = null,

    /* Тип предупреждения */
    @SerialName(value = "alertType") val alertType: kotlin.String? = null,

    @SerialName(value = "button") val button: AlertButton? = null,

    /* Наличие кнопки \"Закрыть\" */
    @SerialName(value = "closeButton") val closeButton: kotlin.Boolean? = null

)

