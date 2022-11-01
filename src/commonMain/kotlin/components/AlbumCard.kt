package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Album

@Composable
fun AlbumCard(album: Album) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            album.coverUri?.let { uri ->
                AsyncImage(
                    load = { loadImageBitmap("https://" + uri.replace("%%", "200x200")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
                )
            }
            Text(album.title, Modifier.width(200.dp), overflow = TextOverflow.Ellipsis, maxLines = 1)
            Text(album.artists?.map { artist -> artist.name }?.joinToString(", ") ?: "Неизвестен",
                Modifier.width(200.dp), overflow = TextOverflow.Ellipsis, maxLines = 1)
            Row(Modifier.width(200.dp), Arrangement.spacedBy(5.dp)) {
                album.year?.let {
                    Text(it.toString())
                }
                album.type?.let {
                    Text(it.toString(), overflow = TextOverflow.Ellipsis)
                }
                album.version?.let {
                    Text(it.toString(), overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }
        }
    }
}