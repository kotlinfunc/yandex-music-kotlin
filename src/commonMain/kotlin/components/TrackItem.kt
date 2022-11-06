package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Track
import navigation.AlbumLocation
import navigation.ArtistLocation
import navigation.Location
import util.time
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackItem(track: Track, onLocationChange: (Location<*>) -> Unit = {}) {
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), Arrangement.spacedBy(5.dp), Alignment.CenterVertically) {
        track.coverUri?.let { uri ->
            AsyncImage(
                load = { loadImageBitmap("https://" + uri.replace("%%", "50x50")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.requiredSize(50.dp)
                    .onClick { onLocationChange(AlbumLocation(track.albums!![0].id)) }
            )
        }
        Column(Modifier.weight(1f)) {
            Text(track.title, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Row {
                track.artists?.map { artist ->
                    Text(
                        artist.name,
                        Modifier.onClick { onLocationChange(ArtistLocation(artist.id)) },
                        overflow = TextOverflow.Ellipsis, maxLines = 1) }
                    ?: Text("Неизвестен")
            }
        }
        IconButton(onClick = {}) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton(onClick = {}) {
            Icon(
                Icons.Filled.NotInterested,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        track.durationMs?.let {
            Text(it.milliseconds.time())
        }
    }
}