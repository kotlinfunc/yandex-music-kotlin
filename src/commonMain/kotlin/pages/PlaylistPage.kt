package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getUserPlaylist
import api.models.Playlist
import api.models.PlaylistId
import api.models.Response
import components.CoverImage
import components.PlaylistCard
import components.TrackItem
import navigation.Location
import util.time
import kotlin.time.Duration.Companion.milliseconds

@Composable
@Preview
fun PlaylistPage(id: PlaylistId, onLocationChange: (Location<*>) -> Unit = {}) {
    var playlistResponse by remember { mutableStateOf<Response<Playlist>?>(null) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        playlistResponse = getUserPlaylist(id)
    }

    if (playlistResponse == null) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (playlistResponse?.error != null) {
        Text("Ошибка: ${playlistResponse?.error?.message}")
    } else {
        playlistResponse?.result?.let {
            val stateVertical = rememberScrollState(0)

            Column {
                Row(Modifier.fillMaxWidth().padding(10.dp)) {
                    CoverImage(it.cover)
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Плейлист")
                            Text("Продолжительность ${it.durationMs!!.milliseconds.time()}")
                        }
                        Text(it.title, fontWeight = FontWeight.Bold, fontSize = 45.sp)
                        it.description?.let {
                            Text(it)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(it.owner.name)
                            Text(it.modified ?: it.created)
                        }
                        Row {
                            Button({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Слушать")
                            }
                            OutlinedButton({}) {
                                Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(it.likesCount?.toString() ?: "0")
                            }
                            IconButton({}) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                    }
                }
                Box {
                    Column(Modifier.padding(10.dp).verticalScroll(stateVertical), Arrangement.spacedBy(10.dp)) {
                        it.tracks?.let {
                            TextField(searchText, { searchText = it },
                                Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                                trailingIcon = {
                                    IconButton({ searchText = "" }, enabled = searchText.isNotBlank())
                                    { Icon(Icons.Filled.Clear, contentDescription = null) }
                                },
                                placeholder = { Text("Поиск") }, singleLine = true)
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                it.filter { it.track.title.contains(searchText)
                                        || it.track.artists?.any { it.name.contains(searchText) } == true }.forEach {
                                    TrackItem(it.track)
                                }
                            }
                        }
                        it.similarPlaylists?.let {
                            Text("Похожие плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Row {
                                it.take(4).forEach {
                                    PlaylistCard(it) { onLocationChange(it) }
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
    }
}