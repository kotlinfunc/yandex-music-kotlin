package panels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import api.getTrack
import api.getTrackSimilar
import api.getTrackSupplement
import api.models.*
import components.AlbumCard
import components.TrackItem
import components.VideoCard
import layouts.Flow
import navigation.AlbumLocation
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TrackInfoPanel(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var trackResponse by remember { mutableStateOf<Response<List<Track>>?>(null) }
    var trackSupplement by remember { mutableStateOf<Response<TrackSupplement>?>(null) }
    var trackSimilarTracks by remember { mutableStateOf<Response<SimilarTracks>?>(null) }

    LaunchedEffect(id) {
        loading = true
        trackResponse = getTrack(id)
        trackSupplement = getTrackSupplement(id)
        trackSimilarTracks = getTrackSimilar(id)
        loading = false
    }

    if (trackResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (trackResponse?.error != null) {
        Text("Ошибка: ${trackResponse?.error?.message}")
    } else {
        trackResponse?.result?.get(0)?.let {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Трек")
                    Text(it.title, it.albums?.get(0)?.id?.let { albumId -> Modifier.onClick { onLocationChange(AlbumLocation(albumId)) } } ?: Modifier,
                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Flow(horizontalSpacing = 10.dp, verticalSpacing = 5.dp) {
                        it.artists?.map { artist ->
                            Text(
                                artist.name,
                                Modifier.onClick { onLocationChange(ArtistLocation(artist.id)) },
                                overflow = TextOverflow.Ellipsis, maxLines = 1) }
                            ?: Text("Неизвестен")
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

                it.albums?.let { albums ->
                    if (albums.isNotEmpty()) {
                        var expanded by remember { mutableStateOf(false) }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Альбомы")
                                IconButton({ expanded = !expanded}) {
                                    Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize))
                                }
                            }

                            if (expanded) {
                                albums.forEach {
                                    AlbumCard(it, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                }

                trackSupplement?.result?.let {
                    it.videos?.let {
                        if (it.isNotEmpty()) {
                            var expanded by remember { mutableStateOf(false) }
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                    Text("Видео")
                                    IconButton({ expanded = !expanded}) {
                                        Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                            contentDescription = null,
                                            modifier = Modifier.size(ButtonDefaults.IconSize))
                                    }
                                }
                                if (expanded) {
                                    Flow(horizontalSpacing = 10.dp, verticalSpacing = 5.dp) {
                                        it.forEach {
                                            VideoCard(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    it.lyrics?.let { lyrics ->
                        var expanded by remember { mutableStateOf(false) }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Текст песни")
                                IconButton({ expanded = !expanded}) {
                                    Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize))
                                }
                            }
                            Text(if (expanded) lyrics.fullLyrics!! else lyrics.lyrics!! + "...")
                        }
                    }
                }

                trackSimilarTracks?.result?.similarTracks?.let { similarTracks ->
                    if (similarTracks.isNotEmpty()) {
                        var expanded by remember { mutableStateOf(false) }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Похожие треки")
                                IconButton({ expanded = !expanded}) {
                                    Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize))
                                }
                            }

                            if (expanded) {
                                similarTracks.forEach {
                                    TrackItem(it, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}