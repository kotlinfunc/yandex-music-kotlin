package pages.tag

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import api.getMetaTagPlayables
import api.models.MetaTag
import api.models.Response
import api.models.SearchType
import components.Pager
import kotlin.math.ceil

private fun partName(searchType: SearchType): String {
    return when (searchType) {
        SearchType.ARTIST -> "исполнители"
        SearchType.ALBUM -> "альбомы"
        SearchType.PLAYLIST -> "плейлисты"
        else -> throw NotImplementedError()
    }
}

@Composable
internal fun <T> Part(metaTag: String, perPage: Int, searchType: SearchType, defaultOrder: MetaTag.SortedBy,
                  pageGetter: (MetaTag) -> List<T>?, content: @Composable (List<T>) -> Unit) {
    var loading by remember { mutableStateOf(true) }
    var order by remember { mutableStateOf(defaultOrder) }
    var pageNumber by remember { mutableStateOf(1) }
    var response by remember { mutableStateOf<Response<MetaTag>?>(null) }
    var orderExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(metaTag, pageNumber, order) {
        orderExpanded = false
        loading = true
        response = getMetaTagPlayables(metaTag, searchType, pageNumber - 1, perPage, order.value)
        loading = false
    }

    if (loading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        response?.result?.let { result ->
            Row {
                Text("${order.title} ${partName(searchType)}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.weight(1f))
                result.sortByValues?.let {
                    Box(Modifier.wrapContentSize(Alignment.TopStart)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(order.title)
                            IconButton({ orderExpanded = !orderExpanded }) {
                                Icon(
                                    if (orderExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                        DropdownMenu(orderExpanded, { orderExpanded = false }) {
                            it.forEach {
                                DropdownMenuItem({ order = it }) {
                                    Text(it.title)
                                }
                            }
                        }
                    }
                }
            }
            content(pageGetter(result)!!)
            result.pager?.let {
                Pager(it.page, ceil(it.total.toDouble() / it.perPage).toInt() ) { pageNumber = it }
            }
        }
    }
}