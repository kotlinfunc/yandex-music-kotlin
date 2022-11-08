package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/metatags/{tag}")
class MetaTags(val tag: String) {
    @Serializable
    @Resource("albums")
    class Albums(val parent: MetaTags, val offset: Int = 0, val limit: Int = 20, val sortBy: String? = null)

    @Serializable
    @Resource("artists")
    class Artists(val parent: MetaTags, val offset: Int = 0, val limit: Int = 20, val sortBy: String? = null)

    @Serializable
    @Resource("playlists")
    class Playlists(val parent: MetaTags, val offset: Int = 0, val limit: Int = 20, val sortBy: String? = null)
}