package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/clips")
class Clips {
    @Serializable
    @Resource("{id}")
    class Get(val parent: Clips = Clips(), val id: Long)
}