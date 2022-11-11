package pages.album

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.Album
import api.models.ArtistAlbums
import api.models.MetaTag
import api.models.Response
import components.AlbumCard
import components.SimpleTrackItem
import layouts.Flow
import layouts.TruncatedRow
import navigation.*

@Composable
internal fun MainView(album: Album, genreAlbumsResponse: Response<MetaTag>?, otherAlbumsResponse: Response<ArtistAlbums>?,
                      onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    val stateVertical = rememberScrollState(0)

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.padding(10.dp).fillMaxWidth().verticalScroll(stateVertical)) {
            album.volumes?.let {
                Column {
                    if (it.size == 1) {
                        it[0].forEachIndexed() { idx, track ->
                            SimpleTrackItem(track, idx + 1, onClick = { onInfoRequest(TrackInfo(track.id)) })
                        }
                    } else {
                        it.forEachIndexed { volumeIdx, volume ->
                            Text("Диск №${volumeIdx + 1}")
                            volume.forEachIndexed() { idx, track ->
                                SimpleTrackItem(track, idx + 1, onClick = { onInfoRequest(TrackInfo(track.id)) })
                            }
                        }
                    }
                }
            }
            album.labels?.let {
                if (it.isNotEmpty()) {
                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append("Лейбл ")
                        }
                        append(it.joinToString(", "))
                    }, fontSize = 13.sp)
                }
            }
            album.duplicates?.let {
                Text("Другие версии альбома", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    it.forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            genreAlbumsResponse?.result?.let {
                if (it.albums?.isNotEmpty() == true) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        Text("Новые альбомы жанра «${it.title.title}»", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        TextButton(onClick = { onLocationChange(MetaTagLocation(it.id)) }) {
                            Text("Смотреть все")
                        }
                    }
                    TruncatedRow(horizontalSpacing = 10.dp) {
                        it.albums.take(10).forEach {
                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                        }
                    }
                }
            }
            otherAlbumsResponse?.result?.albums?.let {
                if (it.isNotEmpty()) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        Text("Другие альбомы исполнителя", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        TextButton(onClick = { onLocationChange(ArtistLocation(album.artists!![0].id)) }) {
                            Text("Смотреть все")
                        }
                    }
                    TruncatedRow(horizontalSpacing = 10.dp) {
                        it.filter { otherAlbum -> otherAlbum.id != album.id }.take(5).forEach {
                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                        }
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