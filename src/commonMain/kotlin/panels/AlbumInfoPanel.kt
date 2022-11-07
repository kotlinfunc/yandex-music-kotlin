package panels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getAlbumWithTracks
import api.models.Album
import api.models.Response
import components.SimpleTrackItem
import layouts.Flow
import navigation.AlbumLocation
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumInfoPanel(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var albumResponse by remember { mutableStateOf<Response<Album>?>(null) }

    LaunchedEffect(id) {
        albumResponse = getAlbumWithTracks(id)
    }

    if (albumResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (albumResponse?.error != null) {
        Text("Ошибка: ${albumResponse?.error?.message}")
    } else {
        albumResponse?.result?.let { album ->
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column {
                    Text("Альбом")
                    Text(album.title, Modifier.onClick { onLocationChange(AlbumLocation(id)) }, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Flow(horizontalSpacing = 10.dp, verticalSpacing = 5.dp) {
                        Text("Исполнитель: ")
                        album.artists?.map { artist ->
                            Text(
                                artist.name,
                                Modifier.onClick { onLocationChange(ArtistLocation(artist.id)) },
                                overflow = TextOverflow.Ellipsis, maxLines = 1) }
                            ?: Text("Неизвестен")
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(album.year?.toString() ?: "Неизвестен")
                        Text(album.genre ?: "Неизвестный жанр")
                    }
                }
                Row {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.PlayCircleFilled,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    IconButton({}) {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }

                album.volumes?.let {
                    if (it.size == 1) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            it[0].forEachIndexed() { idx, track ->
                                SimpleTrackItem(track, idx + 1) { onLocationChange(it) }
                            }
                        }
                    } else {
                        it.forEachIndexed { volumeIdx, volume ->
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Диск №${volumeIdx + 1}")
                                volume.forEachIndexed() { idx, track ->
                                    SimpleTrackItem(track, idx + 1) { onLocationChange(it) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}