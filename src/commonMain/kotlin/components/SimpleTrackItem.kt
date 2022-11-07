package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Track
import navigation.AlbumLocation
import navigation.Location
import util.time
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleTrackItem(track: Track, number: Int, onClick: () -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    Row(Modifier.clickable(onClick = onClick).height(IntrinsicSize.Min), Arrangement.spacedBy(5.dp), Alignment.CenterVertically) {
        Text(number.toString(), textAlign = TextAlign.End)
        Text(track.title, Modifier.weight(0.4f), overflow = TextOverflow.Ellipsis, maxLines = 1)
        Text(track.albums!![0].title, Modifier.weight(0.6f).onClick { onLocationChange(AlbumLocation(track.albums[0].id)) },
            overflow = TextOverflow.Ellipsis, maxLines = 1)
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