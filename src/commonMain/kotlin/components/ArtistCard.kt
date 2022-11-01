package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Artist

@Composable
fun ArtistCard(artist: Artist) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(artist.cover, Icons.Filled.Face)
            Text(artist.name ?: "Неизвестный", Modifier.width(200.dp),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Text(artist.genres?.joinToString(", ") ?: "", Modifier.width(200.dp),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}