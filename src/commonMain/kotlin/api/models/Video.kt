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
 * Видео
 *
 * @param title Название видео
 * @param cover Ссылка на изображение
 * @param embedUrl Ссылка на видео
 * @param provider Сервис поставляющий видео
 * @param providerVideoId Уникальный идентификатор видео на сервисе.
 * @param youtubeUrl Ссылка на видео Youtube
 * @param thumbnailUrl Ссылка на изображение
 * @param duration Длительность видео в секундах
 * @param text Текст
 * @param htmlAutoPlayVideoPlayer HTML тег для встраивания в разметку страницы
 * @param regions 
 */
@Serializable
data class Video (

    /* Название видео */
    @SerialName(value = "title") val title: String,

    /* Ссылка на изображение */
    @SerialName(value = "cover") val cover: String,

    /* Ссылка на видео */
    @SerialName(value = "embedUrl") val embedUrl: String,

    /* Сервис поставляющий видео */
    @SerialName(value = "provider") val provider: String,

    /* Уникальный идентификатор видео на сервисе. */
    @SerialName(value = "providerVideoId") val providerVideoId: String,

    /* Ссылка на видео Youtube */
    @SerialName(value = "youtubeUrl") val youtubeUrl: String? = null,

    /* Ссылка на изображение */
    @SerialName(value = "thumbnailUrl") val thumbnailUrl: String? = null,

    /* Длительность видео в секундах */
    @SerialName(value = "duration") val duration: Double? = null,

    /* Текст */
    @SerialName(value = "text") val text: String? = null,

    /* HTML тег для встраивания в разметку страницы */
    @SerialName(value = "htmlAutoPlayVideoPlayer") val htmlAutoPlayVideoPlayer: String? = null,

    @SerialName(value = "regions") val regions: List<String>? = null

)

