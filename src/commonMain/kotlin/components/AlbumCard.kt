package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explicit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Album
import navigation.AlbumLocation
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AlbumCard(album: Album, onLocationChange: (Location<*>) -> Unit = {}) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            AsyncImage(
                load = { loadImageBitmap("https://" + album.coverUri?.replace("%%", "200x200")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
            )
            Row(Modifier.width(200.dp)) {
                Text(album.title, Modifier.onClick { onLocationChange(AlbumLocation(album.id)) }, overflow = TextOverflow.Ellipsis, maxLines = 1)
                Icon(Icons.Filled.Explicit, null)
            }
            Row {
                album.artists?.map { artist ->
                    Text(
                        artist.name,
                        Modifier.width(200.dp).onClick { onLocationChange(ArtistLocation(artist.id)) },
                        overflow = TextOverflow.Ellipsis, maxLines = 1) }
                    ?: Text("Неизвестен")
            }
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