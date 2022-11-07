package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
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
    Card(Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(artist.cover, CircleShape, Icons.Filled.Face)
            Text(
                artist.name, Modifier.fillMaxWidth().onClick { onLocationChange(ArtistLocation(artist.id)) },
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
            Text(artist.genres?.joinToString(", ") ?: "", Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}