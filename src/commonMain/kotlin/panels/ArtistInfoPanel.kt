package panels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getArtist
import api.models.ArtistInfo
import api.models.Response
import components.AlbumCard
import components.TrackItem
import layouts.Flow
import navigation.ArtistLocation
import navigation.Location

@Composable
internal fun ArtistInfoPanel(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var artistResponse by remember { mutableStateOf<Response<ArtistInfo>?>(null) }

    LaunchedEffect(id) {
        loading = true
        artistResponse = getArtist(id)
        loading = false
    }

    if (loading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (artistResponse?.error != null) {
        Text("Ошибка: ${artistResponse?.error?.message}")
    } else {
        artistResponse?.result?.let {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Исполнитель")
                    Text(it.artist.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
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
                            Icons.Outlined.Favorite,
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
                    Spacer(Modifier.weight(1f))
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Radio,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    OutlinedButton({}, shape = CircleShape) {
                        Icon(
                            Icons.Filled.NotInterested,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
                if (it.popularTracks.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Популярные треки")
                            Spacer(Modifier.weight(1f))
                            TextButton({ onLocationChange(ArtistLocation(it.artist.id)) }) {
                                Text("Все")
                            }
                        }
                        it.popularTracks.take(5).forEach {
                            TrackItem(it, onLocationChange = onLocationChange)
                        }
                    }
                }
                if (it.albums.isNotEmpty()) {
                    Column {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Популярные альбомы")
                            Spacer(Modifier.weight(1f))
                            TextButton({ onLocationChange(ArtistLocation(it.artist.id)) }) {
                                Text("Все")
                            }
                        }
                        Flow(verticalSpacing = 10.dp) {
                            it.albums.take(5).forEach {
                                AlbumCard(it, onLocationChange = onLocationChange)
                            }
                        }
                    }
                }
            }
        }
    }
}