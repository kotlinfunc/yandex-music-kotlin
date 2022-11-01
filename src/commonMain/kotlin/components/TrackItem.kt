package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Track

@Composable
fun TrackItem(track:Track) {
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
            Text(track.title ?: "Неизвестный")
            Text(track.artists?.map { artist -> artist.name }?.joinToString(", ") ?: "Неизвестен")
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