package pages.artist

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.ArtistInfo
import api.models.PlaylistId
import components.*
import layouts.TruncatedRow
import navigation.*
import util.openInBrowser


@Composable
internal fun Overview(artistInfo: ArtistInfo,
                      onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {},
                      onTabChange: (Int) -> Unit) {
    val stateVertical = rememberScrollState(0)
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            if (artistInfo.popularTracks.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Популярные треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(1) }) {
                        Text("Смотреть все")
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    artistInfo.popularTracks.take(5).forEach {
                        TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            if (artistInfo.albums.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Популярные альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(2) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.albums.take(5).forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            if (artistInfo.alsoAlbums.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Популярные сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(2) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.alsoAlbums.take(5).forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            if (artistInfo.playlists?.isNotEmpty() == true) {
                Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.playlists.take(5).forEach {
                        PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
                    }
                }
            }
            if (artistInfo.videos.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Клипы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(3) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.videos.take(5).forEach {
                        VideoCard(it, onClick = { openInBrowser(it.embedUrl) })
                    }
                }
            }
            if (artistInfo.concerts.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(4) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.concerts.take(5).forEach {
                        ConcertCard(it, onClick = { openInBrowser(it.afishaUrl) })
                    }
                }
            }
            if (artistInfo.similarArtists.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(5) }) {
                        Text("Смотреть все")
                    }
                }
                TruncatedRow(horizontalSpacing = 10.dp) {
                    artistInfo.similarArtists.take(5).forEach {
                        ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
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