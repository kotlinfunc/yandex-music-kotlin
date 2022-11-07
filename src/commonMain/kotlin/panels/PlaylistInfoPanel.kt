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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getUserPlaylist
import api.models.Playlist
import api.models.PlaylistId
import api.models.Response
import components.SimpleTrackItem
import navigation.Location
import navigation.PlaylistLocation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistInfoPanel(id: PlaylistId, onLocationChange: (Location<*>) -> Unit = {}) {
    var playlistResponse by remember { mutableStateOf<Response<Playlist>?>(null) }

    LaunchedEffect(id) {
        playlistResponse = getUserPlaylist(id)
    }

    if (playlistResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (playlistResponse?.error != null) {
        Text("Ошибка: ${playlistResponse?.error?.message}")
    } else {
        playlistResponse?.result?.let {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column {
                    Text("Плейлист")
                    Text(it.title, Modifier.onClick { onLocationChange(PlaylistLocation(id)) }, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        it.owner?.let {
                            Text(it.name)
                        }
                        it.modified?.let {
                            Text(it)
                        }
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
                it.tracks?.let {
                    Column {
                        it.forEachIndexed { idx, item ->
                            SimpleTrackItem(item.track, idx + 1) { onLocationChange(it) }
                        }
                    }
                }
            }
        }
    }
}