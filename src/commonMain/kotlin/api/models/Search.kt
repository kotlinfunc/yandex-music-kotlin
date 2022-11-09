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
    val searchRequestId: String,
    val page: Int? = null,
    val perPage: Int? = null,
    /* Текст запроса */
    val text: String,
    //@SerialName(value = "best") val best: Best? = null,
    val albums: PagingResult<Album>? = null,
    val artists: PagingResult<Artist>? = null,
    val playlists: PagingResult<Playlist>? = null,
    val tracks: PagingResult<Track>? = null,
    val videos: PagingResult<Video>? = null,

    //@SerialName(value = "users") val users: SearchResult<BestResult>? = null,

    val podcasts: PagingResult<Podcast>? = null,

    @SerialName(value = "podcast_episodes") val podcastEpisodes: PagingResult<Episode>? = null,

    val type: SearchType? = SearchType.all,

    /* Был ли исправлен запрос */
    val misspellCorrected: Boolean,

    /* Оригинальный запрос */
    val misspellOriginal: String? = null,

    /* Было ли отключено исправление результата */
   val nocorrect: Boolean

)

