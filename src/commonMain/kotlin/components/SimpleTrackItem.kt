package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import api.models.Track
import navigation.AlbumLocation
import navigation.Location
import util.time
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleTrackItem(track: Track, number: Int, onLocationChange: (Location<*>) -> Unit = {}) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(number.toString(), textAlign = TextAlign.End)
        Text(track.title)
        Text(track.albums!![0].title, modifier = Modifier.onClick { onLocationChange(AlbumLocation(track.albums[0].id)) })
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        track.durationMs?.let {
            Text(it.milliseconds.time())
        }
    }
}