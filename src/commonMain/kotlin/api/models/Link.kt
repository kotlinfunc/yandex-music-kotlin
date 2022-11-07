package api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val title: String,
    val href: String,
    val type: Type,
    val socialNetwork: SocialNetwork? = null
) {
    @Serializable
    enum class Type(val value: String) {
        @SerialName(value = "official")
        OFFICIAL("official"),
        @SerialName(value = "social")
        SOCIAL("social"),
    }

    @Serializable
    enum class SocialNetwork(val value: String) {
        @SerialName(value = "facebook")
        FACEBOOK("facebook"),
        @SerialName(value = "tiktok")
        TIKTOK("tiktok"),
        @SerialName(value = "twitter")
        TWITTER("twitter"),
        @SerialName(value = "vk")
        VK("vk"),
        @SerialName(value = "youtube")
        YOUTUBE("youtube"),
    }
}
