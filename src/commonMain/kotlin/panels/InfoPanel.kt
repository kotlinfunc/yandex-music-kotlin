package panels

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.*

@Composable
fun InfoPanel(requestedInfo: Info<*>?, dropInfo: () -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    requestedInfo?.let {
        val stateVertical = rememberScrollState(0)
        Column(Modifier.padding(15.dp, 30.dp).width(300.dp), horizontalAlignment = Alignment.End) {
            IconButton({ dropInfo() }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }

            Box(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                    when (it) {
                        is AlbumInfo -> AlbumInfoPanel(it.data) { onLocationChange(it) }
                        is ArtistInfo -> ArtistInfoPanel(it.data) { onLocationChange(it) }
                        is EpisodeInfo -> EpisodeInfoPanel(it.data) { onLocationChange(it) }
                        is PlaylistInfo -> PlaylistInfoPanel(it.data) { onLocationChange(it) }
                        is PodcastInfo -> PodcastInfoPanel(it.data) { onLocationChange(it) }
                        is TrackInfo -> TrackInfoPanel(it.data) { onLocationChange(it) }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )
            }
        }
    }
}