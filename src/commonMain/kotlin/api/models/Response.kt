package api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T> (
    @SerialName(value = "invocationInfo") val invocationInfo: InvocationInfo,
    @SerialName(value = "result") val result: T? = null,
    @SerialName(value = "error") val error: Error? = null
)