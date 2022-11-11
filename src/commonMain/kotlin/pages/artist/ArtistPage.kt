package pages.artist

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getArtistBriefInfo
import api.models.ArtistInfo
import api.models.Response
import components.ArtistCard
import components.ConcertCard
import components.SimpleTrackItem
import components.VideoCard
import navigation.Info
import navigation.Location
import navigation.TrackInfo
import util.openInBrowser

@Composable
fun ArtistPage(id: Long, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var artistResponse by remember { mutableStateOf<Response<ArtistInfo>?>(null) }

    LaunchedEffect(id) {
        loading = true
        artistResponse = getArtistBriefInfo(id)
        loading = false
    }

    if (loading) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (artistResponse?.error != null) {
        Text("Ошибка: ${artistResponse?.error?.message}")
    } else {
        artistResponse?.result?.let { artistInfo ->
            Column {
                InfoPanel(artistInfo)

                var selectedTab by remember { mutableStateOf(0) }
                val titles = listOf("Главное", "Треки", "Альбомы", "Клипы", "Концерты", "Похожие", "Инфо")
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
                    0 -> Overview(artistInfo, onInfoRequest, onLocationChange) { selectedTab = it }
                    1 -> {
                        val state = rememberLazyListState()
                        Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Box(Modifier.fillMaxSize()) {
                            LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                itemsIndexed(artistInfo.popularTracks) { number, track ->
                                    SimpleTrackItem(track, number + 1, onClick = { onInfoRequest(TrackInfo(track.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                            VerticalScrollbar(
                                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                                adapter = rememberScrollbarAdapter(
                                    scrollState = state
                                )
                            )
                        }
                    }
                    2 -> AlbumView(artistInfo, onInfoRequest, onLocationChange)
                    3 -> {
                        Text("Клипы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(artistInfo.videos) {
                                VideoCard(it, onClick = { openInBrowser(it.embedUrl) })
                            }
                        }
                    }
                    4 -> {
                        Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 278.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(artistInfo.concerts) {
                                ConcertCard(it, onClick = { openInBrowser(it.afishaUrl) })
                            }
                        }
                    }
                    5 -> {
                        Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(artistInfo.similarArtists) {
                                ArtistCard(it, onClick = { onInfoRequest(navigation.ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                            }
                        }
                    }
                    6 -> InfoView(artistInfo)
                }
            }
        }
    }
}