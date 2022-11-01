package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Playlist

@Composable
fun PlaylistCard(playlist: Playlist) {
    Card {
        Column {
            playlist.ogImage?.let { uri ->
                AsyncImage(
                    load = { loadImageBitmap("https://" + uri.replace("%%", "200x200")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
                )
            }
            Text(playlist.title ?: "Неизвестный")
        }
    }
}