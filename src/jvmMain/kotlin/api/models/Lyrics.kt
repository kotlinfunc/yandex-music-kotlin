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
 * текст трека
 *
 * @param id Уникальный идентификатор текста трека.
 * @param lyrics Первые строки текст песни.
 * @param hasRights Есть ли права.
 * @param fullLyrics Текст песни.
 * @param textLanguage Язык текста.
 * @param showTranslation Доступен ли перевод.
 * @param url Ссылка на источник перевода. Обычно genius.com.
 */
@Serializable
data class Lyrics (

    /* Уникальный идентификатор текста трека. */
    @SerialName(value = "id") val id: kotlin.Double? = null,

    /* Первые строки текст песни. */
    @SerialName(value = "lyrics") val lyrics: kotlin.String? = null,

    /* Есть ли права. */
    @SerialName(value = "hasRights") val hasRights: kotlin.Boolean? = null,

    /* Текст песни. */
    @SerialName(value = "fullLyrics") val fullLyrics: kotlin.String? = null,

    /* Язык текста. */
    @SerialName(value = "textLanguage") val textLanguage: kotlin.String? = null,

    /* Доступен ли перевод. */
    @SerialName(value = "showTranslation") val showTranslation: kotlin.Boolean? = null,

    /* Ссылка на источник перевода. Обычно genius.com. */
    @SerialName(value = "url") val url: kotlin.String? = null

)

