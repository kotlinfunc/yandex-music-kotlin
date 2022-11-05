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

import api.models.Cover
import api.models.Owner
import api.models.PlaylistTagsInner
import api.models.TrackItem

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * play list data
 *
 * @param playlistUuid 
 * @param description 
 * @param descriptionFormatted 
 * @param available 
 * @param collective 
 * @param cover 
 * @param created 
 * @param modified 
 * @param backgroundColor 
 * @param textColor 
 * @param durationMs 
 * @param isBunner 
 * @param isPremiere 
 * @param kind 
 * @param ogImage 
 * @param owner 
 * @param prerolls 
 * @param revision 
 * @param snapshot 
 * @param tags 
 * @param title 
 * @param trackCount 
 * @param uid 
 * @param visibility 
 * @param likesCount 
 * @param tracks 
 */
@Serializable
data class Playlist (

    @SerialName(value = "playlistUuid") val playlistUuid: String? = null,

    @SerialName(value = "description") val description: String? = null,

    @SerialName(value = "descriptionFormatted") val descriptionFormatted: String? = null,

    @SerialName(value = "available") val available: Boolean? = null,

    @SerialName(value = "collective") val collective: Boolean? = null,

    @SerialName(value = "cover") val cover: Cover? = null,

    @SerialName(value = "created") val created: String,

    @SerialName(value = "modified") val modified: String? = null,

    @SerialName(value = "backgroundColor") val backgroundColor: String? = null,

    @SerialName(value = "textColor") val textColor: kotlin.String? = null,

    @SerialName(value = "durationMs") val durationMs: Int? = null,

    @SerialName(value = "isBunner") val isBunner: kotlin.Boolean? = null,

    @SerialName(value = "isPremiere") val isPremiere: kotlin.Boolean? = null,

    @SerialName(value = "kind") val kind: Long,

    @SerialName(value = "ogImage") val ogImage: String? = null,

    @SerialName(value = "owner") val owner: Owner,

    @SerialName(value = "prerolls") val prerolls: List<String>? = null,

    @SerialName(value = "revision") val revision: Int? = null,

    @SerialName(value = "snapshot") val snapshot: Int? = null,

    @SerialName(value = "tags") val tags: List<PlaylistTagsInner>? = null,

    @SerialName(value = "title") val title: String,

    @SerialName(value = "trackCount") val trackCount: Int? = null,

    @SerialName(value = "uid") val uid: Long,

    @SerialName(value = "visibility") val visibility: Visibility? = null,

    @SerialName(value = "likesCount") val likesCount: Int? = null,

    @SerialName(value = "tracks") val tracks: List<TrackItem>? = null,

    val similarPlaylists: List<Playlist>? = null,

    val lastOwnerPlaylists: List<Playlist>? = null

) {

    /**
     * 
     *
     * Values: `public`,`private`
     */
    @Serializable
    enum class Visibility(val value: String) {
        @SerialName(value = "public") `public`("public"),
        @SerialName(value = "private") `private`("private");
    }
}

