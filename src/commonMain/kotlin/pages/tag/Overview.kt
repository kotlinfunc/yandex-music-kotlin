package pages.tag

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.MetaTag
import api.models.PlaylistId
import components.AlbumCard
import components.ArtistCard
import components.PlaylistCard
import layouts.Flow
import navigation.*

@Composable
internal fun Overview(metaTag: MetaTag,
                      onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {},
                      onTabChange: (Int) -> Unit) {
    val stateVertical = rememberScrollState(0)

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            if (metaTag.albums?.isNotEmpty() == true) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text(
                        "${metaTag.albumsSortByValues!!.first { it.active }.title} альбомы",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    TextButton(onClick = { onTabChange(1) }) {
                        Text("Все")
                    }
                }
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    metaTag.albums.take(10).forEach {
                        AlbumCard(
                            it,
                            onClick = { onInfoRequest(AlbumInfo(it.id)) },
                            onLocationChange = onLocationChange
                        )
                    }
                }
            }
            if (metaTag.artists?.isNotEmpty() == true) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text("Популярные исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    TextButton(onClick = { onTabChange(2) }) {
                        Text("Все")
                    }
                }
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    metaTag.artists.take(10).forEach {
                        ArtistCard(
                            it,
                            onClick = { onInfoRequest(ArtistInfo(it.id)) },
                            onLocationChange = onLocationChange
                        )
                    }
                }
            }
            if (metaTag.playlists?.isNotEmpty() == true) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Text(
                        "${metaTag.playlistsSortByValues!!.first { it.active }.title} плейлисты",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    TextButton(onClick = { onTabChange(3) }) {
                        Text("Все")
                    }
                }
                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                    metaTag.playlists.take(10).forEach {
                        PlaylistCard(
                            it,
                            onClick = {
                                onInfoRequest(
                                    PlaylistInfo(
                                        PlaylistId(
                                            it.uid,
                                            it.kind
                                        )
                                    )
                                )
                            },
                            onLocationChange = onLocationChange
                        )
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