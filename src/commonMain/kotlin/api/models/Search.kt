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
 * Результаты поиска
 *
 * @param searchResultId ID запроса
 * @param text Текст запроса
 * @param best 
 * @param albums 
 * @param artists 
 * @param playlists 
 * @param tracks 
 * @param videos 
 * @param users 
 * @param podcasts 
 * @param podcastEpisodes 
 * @param type 
 * @param page Текущая страница
 * @param perPage Результатов на странице
 * @param misspellCorrected Был ли исправлен запрос
 * @param misspellOriginal Оригинальный запрос
 * @param nocorrect Было ли отключено исправление результата
 */
@Serializable
data class Search (

    /* ID запроса */
    @SerialName(value = "searchRequestId") val searchRequestId: String,

    /* Текст запроса */
    @SerialName(value = "text") val text: String,

    //@SerialName(value = "best") val best: Best? = null,

    @SerialName(value = "albums") val albums: PagingResult<Album>? = null,

    @SerialName(value = "artists") val artists: PagingResult<Artist>? = null,

    @SerialName(value = "playlists") val playlists: PagingResult<Playlist>? = null,

    @SerialName(value = "tracks") val tracks: PagingResult<Track>? = null,

    @SerialName(value = "videos") val videos: PagingResult<Video>? = null,

    //@SerialName(value = "users") val users: SearchResult<BestResult>? = null,

    @SerialName(value = "podcasts") val podcasts: PagingResult<Podcast>? = null,

    @SerialName(value = "podcast_episodes") val podcastEpisodes: PagingResult<Episode>? = null,

    @SerialName(value = "type") val type: SearchType? = SearchType.all,

    /* Был ли исправлен запрос */
    @SerialName(value = "misspellCorrected") val misspellCorrected: Boolean,

    /* Оригинальный запрос */
    @SerialName(value = "misspellOriginal") val misspellOriginal: String? = null,

    /* Было ли отключено исправление результата */
    @SerialName(value = "nocorrect") val nocorrect: Boolean

)

