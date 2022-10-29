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

import org.openapitools.client.models.UserSettings
import org.openapitools.client.models.VisibilityEnum

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param uid 
 * @param lastFmScrobblingEnabled 
 * @param facebookScrobblingEnabled 
 * @param shuffleEnabled 
 * @param addNewTrackOnPlaylistTop 
 * @param volumePercents 
 * @param userMusicVisibility 
 * @param userSocialVisibility 
 * @param adsDisabled 
 * @param modified 
 * @param rbtDisabled 
 * @param theme Тема оформления.
 * @param promosDisabled 
 * @param autoPlayRadio 
 * @param syncQueueEnabled 
 */
@Serializable
data class AccountSettingsGet200Response1Result (

    @SerialName(value = "uid") val uid: kotlin.Double? = null,

    @SerialName(value = "lastFmScrobblingEnabled") val lastFmScrobblingEnabled: kotlin.Boolean? = null,

    @SerialName(value = "facebookScrobblingEnabled") val facebookScrobblingEnabled: kotlin.Boolean? = null,

    @SerialName(value = "shuffleEnabled") val shuffleEnabled: kotlin.Boolean? = null,

    @SerialName(value = "addNewTrackOnPlaylistTop") val addNewTrackOnPlaylistTop: kotlin.Boolean? = null,

    @SerialName(value = "volumePercents") val volumePercents: kotlin.Boolean? = null,

    @SerialName(value = "userMusicVisibility") val userMusicVisibility: VisibilityEnum? = null,

    @SerialName(value = "userSocialVisibility") val userSocialVisibility: VisibilityEnum? = null,

    @SerialName(value = "adsDisabled") val adsDisabled: kotlin.Boolean? = null,

    @SerialName(value = "modified") val modified: kotlin.String? = null,

    @SerialName(value = "rbtDisabled") val rbtDisabled: kotlin.String? = null,

    /* Тема оформления. */
    @SerialName(value = "theme") val theme: AccountSettingsGet200Response1Result.Theme? = null,

    @SerialName(value = "promosDisabled") val promosDisabled: kotlin.Boolean? = null,

    @SerialName(value = "autoPlayRadio") val autoPlayRadio: kotlin.Boolean? = null,

    @SerialName(value = "syncQueueEnabled") val syncQueueEnabled: kotlin.Boolean? = null

) {

    /**
     * Тема оформления.
     *
     * Values: black,default
     */
    @Serializable
    enum class Theme(val value: kotlin.String) {
        @SerialName(value = "black") black("black"),
        @SerialName(value = "default") default("default");
    }
}
