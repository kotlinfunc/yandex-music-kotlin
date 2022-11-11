package api.resources

import api.models.SearchType
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/search")
class Search(val text: String? = "", val page: Int? = 0, val pageSize: Int? = 0, val type: SearchType? = SearchType.ALL, val nococrrect: Boolean? = false) {

    @Serializable
    @Resource("suggest")
    class Suggest(val parent: Search = Search(null, null, null, null, null), val part: String)

    @Serializable
    @Resource("suggest2")
    class Suggest2(val parent: Search = Search(null, null, null, null, null), val part: String)
}