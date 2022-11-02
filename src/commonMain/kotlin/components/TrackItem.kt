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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Track
import navigation.AlbumLocation
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackItem(track: Track, onLocationChange: (Location<*>) -> Unit = {}) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        track.coverUri?.let { uri ->
            AsyncImage(
                load = { loadImageBitmap("https://" + uri.replace("%%", "200x200")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(50.dp, 50.dp).width(50.dp).height(50.dp)
            )
        }
        Spacer(Modifier.size(5.dp))
        Column {
            Text(track.title ?: "Неизвестный", Modifier.onClick { onLocationChange(AlbumLocation(track.albums!![0].id)) })
            Row {
                track.artists?.map { artist ->
                    Text(artist.name!!,
                        Modifier.onClick { onLocationChange(ArtistLocation(artist.id)) }) }
                    ?: Text("Неизвестен")
            }
        }
        Spacer(Modifier.fillMaxWidth())
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Spacer(Modifier.size(5.dp))
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Spacer(Modifier.size(5.dp))
        Text("3:03")
    }
}