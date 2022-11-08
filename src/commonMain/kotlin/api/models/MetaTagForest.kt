package api.models

import kotlinx.serialization.Serializable

@Serializable
data class MetaTagForest(val trees: List<Node>) {
    @Serializable
    data class Node(
        val title: String,
        val navigationId: String? = null,
        val leaves: List<Node>? = null
    )
}