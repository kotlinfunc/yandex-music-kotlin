package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Artist

@OptIn(ExperimentalMaterial3Api::class)
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