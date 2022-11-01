package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Artist

@Composable
fun ArtistCard(artist: Artist) {
    Card {
        Column {
            artist.cover?.uri?.let { uri ->
                AsyncImage(
                    load = { loadImageBitmap("https://" + uri.replace("%%", "200x200")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
                )
            }
            Text(artist.name ?: "Неизвестный")
            Text(artist.genres?.joinToString(", ") ?: "")
        }
    }
}