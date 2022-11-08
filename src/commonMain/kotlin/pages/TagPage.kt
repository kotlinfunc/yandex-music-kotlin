package pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getPlaylists
import api.getTagPlaylistIds
import api.models.Playlist
import api.models.PlaylistId
import api.models.Response
import components.PlaylistCard
import layouts.TruncatedRow
import navigation.Info
import navigation.Location
import navigation.PlaylistInfo

@Composable
fun TagPage(tag: String, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var playlistsResponse by remember { mutableStateOf<Response<List<Playlist>>?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Обзор", "Плейлисты")

    LaunchedEffect(tag) {
        loading = true
        val playlistIds = getTagPlaylistIds(tag)
        if (playlistIds.result?.ids?.isNotEmpty() == true) {
            playlistsResponse = getPlaylists(playlistIds.result.ids)
        }
        loading = false
    }

    if (loading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        playlistsResponse?.result?.let {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Жанр", fontWeight = FontWeight.Bold, fontSize = 45.sp)
                TabRow(selectedTab) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
                when (selectedTab) {
                    0 -> {
                        if (it.isNotEmpty()) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                TextButton(onClick = { selectedTab = 1 }) {
                                    Text("Все")
                                }
                            }
                            TruncatedRow {
                                TruncatedRow(horizontalSpacing = 10.dp) {
                                    it.take(5).forEach {
                                        PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(it) {
                                PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
                            }
                        }
                    }
                }
            }
        }
    }
}