package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/tracks")
class Tracks {
    @Serializable
    @Resource("{id}")
    class Get(val parent: Tracks = Tracks(), val id: Long) {
        @Serializable
        @Resource("download-info")
        class DownloadInfo(val parent: Get)

        @Serializable
        @Resource("similar")
        class Similar(val parent: Get)

        @Serializable
        @Resource("supplement")
        class Supplement(val parent: Get)
    }
}