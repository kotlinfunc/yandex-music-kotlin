package api.resources

import api.models.ChartScope
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/landing3")
class Landing {
    @Serializable
    @Resource("chart/{chartType}")
    class Chart(val parent: Landing = Landing(), val chartType: ChartScope)
}