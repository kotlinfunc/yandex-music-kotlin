package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Album

@Composable
fun AlbumCard(album: Album) {
    Column {
        album.coverUri?.let { uri ->
            AsyncImage(
                load = { loadImageBitmap("https://" + uri.replace("%%", "200x200")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
            )
        }
        Text(album.title)
        Text(album.artists?.map { artist -> artist.name }?.joinToString(", ") ?: "Неизвестен")
        Row {
            album.year?.let {
                Text(it.toString())
            }
            album.type?.let {
                Text(it.toString())
            }
        }
    }
}