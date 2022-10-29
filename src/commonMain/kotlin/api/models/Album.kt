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

import org.openapitools.client.models.AlbumLabelsInner
import org.openapitools.client.models.Artist
import org.openapitools.client.models.Track

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param id Идентификатор альбома
 * @param error Ошибка получения альбома
 * @param title Название альбома
 * @param type Тип альбома
 * @param metaType Мета тип
 * @param year Год релиза
 * @param releaseDate Дата релиза в формате ISO 8601
 * @param coverUri Ссылка на обложку
 * @param ogImage Ссылка на превью Open Graph
 * @param genre Жанр музыки
 * @param buy 
 * @param trackCount Количество треков.
 * @param recent Является ли альбом новым
 * @param veryImportant Популярен ли альбом у слушателей
 * @param artists Артисты
 * @param labels Лейблы
 * @param available Доступен ли альбом
 * @param availableForPremiumUsers Доступен ли альбом для пользователей с подпиской
 * @param availableForMobile Доступен ли альбом из приложения для телефона
 * @param availablePartially Доступен ли альбом частично для пользователей без подписки
 * @param bests ID лучших треков альбома
 * @param prerolls Прероллы
 * @param volumes Треки альбома, разделенные по дискам.
 */
@Serializable
data class Album (

    /* Идентификатор альбома */
    @SerialName(value = "id") val id: kotlin.Double? = null,

    /* Ошибка получения альбома */
    @SerialName(value = "error") val error: kotlin.String? = null,

    /* Название альбома */
    @SerialName(value = "title") val title: kotlin.String? = null,

    /* Тип альбома */
    @SerialName(value = "type") val type: Album.Type? = null,

    /* Мета тип */
    @SerialName(value = "metaType") val metaType: Album.MetaType? = null,

    /* Год релиза */
    @SerialName(value = "year") val year: kotlin.Double? = null,

    /* Дата релиза в формате ISO 8601 */
    @SerialName(value = "releaseDate") val releaseDate: kotlin.String? = null,

    /* Ссылка на обложку */
    @SerialName(value = "coverUri") val coverUri: kotlin.String? = null,

    /* Ссылка на превью Open Graph */
    @SerialName(value = "ogImage") val ogImage: kotlin.String? = null,

    /* Жанр музыки */
    @SerialName(value = "genre") val genre: kotlin.String? = null,

    @SerialName(value = "buy") val buy: kotlin.collections.List<kotlin.String>? = null,

    /* Количество треков. */
    @SerialName(value = "trackCount") val trackCount: kotlin.Double? = null,

    /* Является ли альбом новым */
    @SerialName(value = "recent") val recent: kotlin.Boolean? = null,

    /* Популярен ли альбом у слушателей */
    @SerialName(value = "veryImportant") val veryImportant: kotlin.Boolean? = null,

    /* Артисты */
    @SerialName(value = "artists") val artists: kotlin.collections.List<Artist>? = null,

    /* Лейблы */
    @SerialName(value = "labels") val labels: kotlin.collections.List<AlbumLabelsInner>? = null,

    /* Доступен ли альбом */
    @SerialName(value = "available") val available: kotlin.Boolean? = null,

    /* Доступен ли альбом для пользователей с подпиской */
    @SerialName(value = "availableForPremiumUsers") val availableForPremiumUsers: kotlin.Boolean? = null,

    /* Доступен ли альбом из приложения для телефона */
    @SerialName(value = "availableForMobile") val availableForMobile: kotlin.Boolean? = null,

    /* Доступен ли альбом частично для пользователей без подписки */
    @SerialName(value = "availablePartially") val availablePartially: kotlin.Boolean? = null,

    /* ID лучших треков альбома */
    @SerialName(value = "bests") val bests: kotlin.collections.List<kotlin.Double>? = null,

    /* Прероллы */
    @SerialName(value = "prerolls") val prerolls: kotlin.collections.List<kotlin.String>? = null,

    /* Треки альбома, разделенные по дискам. */
    @SerialName(value = "volumes") val volumes: kotlin.collections.List<kotlin.collections.List<Track>>? = null

) {

    /**
     * Тип альбома
     *
     * Values: single,podcast
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "single") single("single"),
        @SerialName(value = "podcast") podcast("podcast");
    }
    /**
     * Мета тип
     *
     * Values: single,podcast,music
     */
    @Serializable
    enum class MetaType(val value: kotlin.String) {
        @SerialName(value = "single") single("single"),
        @SerialName(value = "podcast") podcast("podcast"),
        @SerialName(value = "music") music("music");
    }
}
