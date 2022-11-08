package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/tags")
class Tags {
    @Serializable
    @Resource("{tag}")
    class Get(val parent: Tags = Tags(), val tag: String){
        @Serializable
        @Resource("playlist-ids")
        class PlaylistIds(val parent: Get)
    }
}