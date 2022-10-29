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

import org.openapitools.client.models.Album
import org.openapitools.client.models.InvocationInfo

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param invocationInfo 
 * @param result 
 */
@Serializable
data class AlbumsAlbumIdGet200Response (

    @SerialName(value = "invocationInfo") val invocationInfo: InvocationInfo? = null,

    @SerialName(value = "result") val result: Album? = null

)

