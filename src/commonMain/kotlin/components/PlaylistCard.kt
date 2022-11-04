package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Playlist
import navigation.ArtistLocation
import navigation.Location
import navigation.PlaylistLocation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlaylistCard(playlist: Playlist, onLocationChange: (Location<*>) -> Unit = {}) {
    Card(Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            CoverImage(playlist.cover)
            Text(playlist.title ?: "Неизвестный",
                Modifier.fillMaxWidth().onClick { onLocationChange(PlaylistLocation(playlist.uid)) },
                overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}