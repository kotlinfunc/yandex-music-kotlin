package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Playlist

@Composable
fun PlaylistCard(playlist: Playlist) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(playlist.cover)
            Text(playlist.title ?: "Неизвестный", Modifier.width(200.dp), overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}