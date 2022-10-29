package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/search")
class Search(val text: String? = "", val page: Int? = 0, val type: String? = "all", val nococrrect: Boolean? = false) {

    @Serializable
    @Resource("suggest")
    class Suggest(val parent: Search = Search(null, null, null, null), val part: String)
}