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

import api.serializers.AbstractLabelSerializer
import api.serializers.SimpleLabelSerializer
import kotlinx.serialization.*

@Serializable(with = AbstractLabelSerializer::class)
sealed class AbstractLabel {
    abstract val name: String

    override fun toString(): String {
        return name
    }
}

@Serializable(with = SimpleLabelSerializer::class)
class SimpleLabel(override val name: String): AbstractLabel()

/**
 * 
 *
 * @param id 
 * @param name 
 */
@Serializable
class Label (
    val id: Long,
    override val name: String
): AbstractLabel()