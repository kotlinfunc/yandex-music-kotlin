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
 * информация о вариантах загрузки трека
 *
 * @param codec Кодек аудиофайла
 * @param gain Усиление
 * @param preview Предварительный просмотр
 * @param downloadInfoUrl Ссылка на XML документ содержащий данные для загрузки трека
 * @param direct Прямая ли ссылка
 * @param bitrateInKbps Битрейт аудиофайла в кбит/с
 */
@Serializable
data class DownloadInfo (

    /* Кодек аудиофайла */
    @SerialName(value = "codec") val codec: kotlin.String? = null,

    /* Усиление */
    @SerialName(value = "gain") val gain: kotlin.Boolean? = null,

    /* Предварительный просмотр */
    @SerialName(value = "preview") val preview: kotlin.String? = null,

    /* Ссылка на XML документ содержащий данные для загрузки трека */
    @SerialName(value = "downloadInfoUrl") val downloadInfoUrl: kotlin.String? = null,

    /* Прямая ли ссылка */
    @SerialName(value = "direct") val direct: kotlin.Boolean? = null,

    /* Битрейт аудиофайла в кбит/с */
    @SerialName(value = "bitrateInKbps") val bitrateInKbps: kotlin.Double? = null

)

