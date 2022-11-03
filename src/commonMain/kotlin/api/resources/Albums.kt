package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/albums")
class Albums {
    @Serializable
    @Resource("{id}")
    class Get(val parent: Albums = Albums(), val id: Long) {
        @Serializable
        @Resource("with-tracks")
        class WithTracks(val parent: Get)
    }
}