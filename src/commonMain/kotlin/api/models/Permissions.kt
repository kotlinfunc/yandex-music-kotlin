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
 * Информация о правах пользователя, их изначальных значениях и даты окончания
 *
 * @param until 
 * @param propertyValues 
 * @param default 
 */
@Serializable
data class Permissions (

    @SerialName(value = "until") val until: kotlin.String? = null,

    @SerialName(value = "values") val propertyValues: kotlin.collections.List<Permissions.PropertyValues>? = null,

    @SerialName(value = "default") val default: kotlin.collections.List<Permissions.Default>? = null

) {

    /**
     * 
     *
     * Values: landingMinusPlay,feedMinusPlay,radioMinusPlay,mixMinusPlay,radioMinusSkips,playMinusRadioMinusFullMinusTracks
     */
    @Serializable
    enum class PropertyValues(val value: kotlin.String) {
        @SerialName(value = "landing-play") landingMinusPlay("landing-play"),
        @SerialName(value = "feed-play") feedMinusPlay("feed-play"),
        @SerialName(value = "radio-play") radioMinusPlay("radio-play"),
        @SerialName(value = "mix-play") mixMinusPlay("mix-play"),
        @SerialName(value = "radio-skips") radioMinusSkips("radio-skips"),
        @SerialName(value = "play-radio-full-tracks") playMinusRadioMinusFullMinusTracks("play-radio-full-tracks");
    }
    /**
     * 
     *
     * Values: landingMinusPlay,feedMinusPlay,radioMinusPlay,mixMinusPlay,radioMinusSkips,playMinusRadioMinusFullMinusTracks
     */
    @Serializable
    enum class Default(val value: kotlin.String) {
        @SerialName(value = "landing-play") landingMinusPlay("landing-play"),
        @SerialName(value = "feed-play") feedMinusPlay("feed-play"),
        @SerialName(value = "radio-play") radioMinusPlay("radio-play"),
        @SerialName(value = "mix-play") mixMinusPlay("mix-play"),
        @SerialName(value = "radio-skips") radioMinusSkips("radio-skips"),
        @SerialName(value = "play-radio-full-tracks") playMinusRadioMinusFullMinusTracks("play-radio-full-tracks");
    }
}

