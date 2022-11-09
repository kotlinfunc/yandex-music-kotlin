package pages.search

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.PlaylistId
import api.models.Search
import components.*
import layouts.TruncatedRow
import navigation.*

@Composable
internal fun Overview(overviewResult: Search,
                      onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {},
                      onTabChange: (Int) -> Unit) {

    val stateVertical = rememberScrollState(0)
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            overviewResult.artists?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(1) }) {
                        Text("Смотреть всех")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    it.results.take(10).forEach {
                        ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            overviewResult.albums?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(2) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    it.results.take(10).forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            overviewResult.tracks?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(3) }) {
                        Text("Смотреть все")
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    it.results.take(10).forEach {
                        TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            overviewResult.podcasts?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Подкасты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(4) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    it.results.take(10).forEach {
                        PodcastCard(it, onClick = { onInfoRequest(PodcastInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            overviewResult.podcastEpisodes?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Выпуски", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(5) }) {
                        Text("Смотреть все")
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    it.results.take(10).forEach {
                        EpisodeItem(it, onClick = { onInfoRequest(EpisodeInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            overviewResult.playlists?.let {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(6) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    it.results.take(10).forEach {
                        PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
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