package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/chart")
class Chart {
    @Serializable
    @Resource("albums")
    class Albums(val parent: Chart = Chart())

    @Serializable
    @Resource("podcasts")
    class Podcasts(val parent: Chart = Chart())
}