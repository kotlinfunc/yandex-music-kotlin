package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Artist
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArtistCard(artist: Artist, onLocationChange: (Location<*>) -> Unit = {}) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(artist.cover, Icons.Filled.Face)
            Text(
                artist.name, Modifier.width(200.dp).onClick { onLocationChange(ArtistLocation(artist.id)) },
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Text(artist.genres?.joinToString(", ") ?: "", Modifier.width(200.dp),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}