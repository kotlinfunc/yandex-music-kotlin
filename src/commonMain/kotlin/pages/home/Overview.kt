package pages.home

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.Chart
import api.models.Feed
import api.models.Response
import components.AlbumCard
import components.TrackItem
import layouts.TruncatedRow
import navigation.AlbumInfo
import navigation.Info
import navigation.Location
import navigation.TrackInfo

@Composable
internal fun Overview(feedResponse: Response<Feed>?, chartResponse: Response<Chart>?,
                      onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {},
                      onTabChange: (Int) -> Unit) {
    val newAlbums = feedResponse?.result?.days?.get(0)?.events?.first { event -> event is Feed.NewAlbumsOfFavoriteGenre }?.let {
        (it as Feed.NewAlbumsOfFavoriteGenre).albums
    }
    val chartInfo = chartResponse?.result

    if (newAlbums == null || chartInfo == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        val stateVertical = rememberScrollState(0)
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical), Arrangement.spacedBy(10.dp)) {
                Text("Главное", fontWeight = FontWeight.Bold, fontSize = 45.sp)
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Новые релизы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton({ onTabChange(0) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    newAlbums.take(5).forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Column {
                        Text(chartInfo.chart.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(chartInfo.title)
                    }
                    TextButton({ onTabChange(1) }) {
                        Text("Смотреть все")
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    chartInfo.chart.tracks?.take(10)?.let {
                        it.forEach {
                            TrackItem(it.track!!, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                        }
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical)
            )
        }
    }
}