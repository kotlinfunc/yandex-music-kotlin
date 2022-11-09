package pages.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import api.models.PagingResult
import api.models.Response
import api.models.Search
import api.models.SearchType
import api.search
import components.Pager
import kotlin.math.ceil

@Composable
internal fun <T> Part(query: String, perPage: Int, searchType: SearchType,
                      pageGetter: (Search) -> PagingResult<T>?, content: @Composable (PagingResult<T>) -> Unit) {
    var loading by remember { mutableStateOf(true) }
    var pageNumber by remember { mutableStateOf(1) }
    var response by remember { mutableStateOf<Response<Search>?>(null) }

    LaunchedEffect(query, pageNumber) {
        loading = true
        response = search(query, pageNumber - 1, perPage, searchType)
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
        response?.result?.let(pageGetter)?.let { page ->
            content(page)
            Pager(pageNumber, ceil(page.total.toDouble() / page.perPage).toInt() ) { pageNumber = it }
        }
    }
}