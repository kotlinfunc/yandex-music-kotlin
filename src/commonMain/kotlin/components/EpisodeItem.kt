package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import api.models.Episode
import navigation.Location
import navigation.PodcastLocation
import util.time
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun EpisodeItem(episode: Episode, onLocationChange: (Location<*>) -> Unit = {}) {
    Row(Modifier.height(IntrinsicSize.Min).fillMaxWidth(), Arrangement.spacedBy(5.dp), Alignment.CenterVertically) {
        AsyncImage(
            load = { loadImageBitmap("https://" + episode.coverUri.replace("%%", "50x50")) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.requiredSize(50.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(episode.title)
            Text(episode.albums[0].title, Modifier.onClick { onLocationChange(PodcastLocation(episode.albums[0].id)) })
        }
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
        Text(episode.durationMs.milliseconds.time())
    }
}