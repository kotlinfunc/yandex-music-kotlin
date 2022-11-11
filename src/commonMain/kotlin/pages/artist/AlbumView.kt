package pages.artist

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.ArtistInfo
import components.AlbumCard
import layouts.Flow
import navigation.AlbumInfo
import navigation.Info
import navigation.Location

@Composable
internal fun AlbumView(artistInfo: ArtistInfo,
              onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    val stateVertical = rememberScrollState(0)
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            if (artistInfo.albums.isNotEmpty()) {
                Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    artistInfo.albums.forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }
            if (artistInfo.alsoAlbums.isNotEmpty()) {
                Text("Сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    artistInfo.alsoAlbums.forEach {
                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                    }
                }
            }

        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}