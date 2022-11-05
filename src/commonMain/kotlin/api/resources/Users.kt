package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class Users {
    @Serializable
    @Resource("{id}")
    class Get(val parent: Users = Users(), val id: Long) {
        @Serializable
        @Resource("playlists")
        class Playlists(val parent: Get) {
            @Serializable
            @Resource("{kind}")
            class ByKind(val parent: Playlists, val kind: Long)

            @Serializable
            @Resource("list")
            class List(val parent: Playlists)
        }
    }
}