package api.resources

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource("/artists")
class Artists {
    @Serializable
    @Resource("{id}")
    class Get(val parent: Artists = Artists(), val id: Long) {
        @Serializable
        @Resource("brief-info")
        class BriefInfo(val parent: Get)

        @Serializable
        @Resource("direct-albums")
        class DirectAlbums(val parent: Get,
                           val page: Int = 0,
                           val pageSize: Int = 20,
                           @SerialName("sort-by") val sortBy: String = "rating")

        @Serializable
        @Resource("track-ids-by-rating")
        class TrackIdsByRating(val parent: Get)

        @Serializable
        @Resource("tracks")
        class Tracks(val parent: Get, val page: Int = 0, val pageSize: Int = 20)
    }
}