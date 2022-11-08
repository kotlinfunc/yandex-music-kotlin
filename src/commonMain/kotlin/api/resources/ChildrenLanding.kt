package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/children-landing")
class ChildrenLanding {
    @Serializable
    @Resource("catalogue")
    class Catalogue(val parent: ChildrenLanding = ChildrenLanding())

    @Serializable
    @Resource("category/{name}")
    class CategoryByName(val parent: ChildrenLanding = ChildrenLanding(), val name: String) {
        @Serializable
        @Resource("albums")
        class Albums(val parent: CategoryByName)
    }

    @Serializable
    @Resource("editorial")
    class Editorial {
        @Serializable
        @Resource("album/{name}")
        class AlbumByName(val parent: Editorial = Editorial(), val name: String)

        @Serializable
        @Resource("playlist/{name}")
        class PlaylistByName(val parent: Editorial = Editorial(), val name: String)
    }

    @Serializable
    @Resource("compilations/{id}")
    class CompilationById(val parent: ChildrenLanding = ChildrenLanding(), val id: Long)
}