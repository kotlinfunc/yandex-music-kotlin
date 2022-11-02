package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Playlist
import navigation.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistCard(playlist: Playlist, onLocationChange: (Location<*>) -> Unit = {}) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(playlist.cover)
            Text(playlist.title ?: "Неизвестный", Modifier.width(200.dp), overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}