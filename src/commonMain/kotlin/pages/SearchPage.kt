package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.*

@Composable
@Preview
fun SearchPage() {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Исполнители", "Альбомы", "Треки", "Подкасты", "Выпуски", "Плейлисты")

    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when(selectedTab) {
            0 -> {
                Box(Modifier.fillMaxSize()) {
                    val stateVertical = rememberScrollState(0)

                    Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Исполнители")
                            Button(onClick = { selectedTab = 1 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ArtistCard()
                            ArtistCard()
                            ArtistCard()
                            ArtistCard()
                            ArtistCard()
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Альбомы")
                            Button(onClick = { selectedTab = 2 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            AlbumCard()
                            AlbumCard()
                            AlbumCard()
                            AlbumCard()
                            AlbumCard()
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Треки")
                            Button(onClick = { selectedTab = 3 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            TrackItem()
                            TrackItem()
                            TrackItem()
                            TrackItem()
                            TrackItem()
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Подкасты")
                            Button(onClick = { selectedTab = 4 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            PodcastCard()
                            PodcastCard()
                            PodcastCard()
                            PodcastCard()
                            PodcastCard()
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Выпуски")
                            Button(onClick = { selectedTab = 5 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            EpisodeItem()
                            EpisodeItem()
                            EpisodeItem()
                            EpisodeItem()
                            EpisodeItem()
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Плейлисты")
                            Button(onClick = { selectedTab = 6 }) {
                                Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            PlaylistCard()
                            PlaylistCard()
                            PlaylistCard()
                            PlaylistCard()
                            PlaylistCard()
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(stateVertical)
                    )
                }
            }
            1 -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    item {
                        ArtistCard()
                    }
                    item {
                        ArtistCard()
                    }
                    item {
                        ArtistCard()
                    }
                    item {
                        ArtistCard()
                    }
                    item {
                        ArtistCard()
                    }
                    item {
                        ArtistCard()
                    }
                }
            }
            2 -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    item {
                        AlbumCard()
                    }
                    item {
                        AlbumCard()
                    }
                    item {
                        AlbumCard()
                    }
                    item {
                        AlbumCard()
                    }
                    item {
                        AlbumCard()
                    }
                    item {
                        AlbumCard()
                    }
                }
            }
            3 -> {
                Box(Modifier.fillMaxSize()) {
                    val state = rememberLazyListState()
                    LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp)) {
                        item {
                            TrackItem()
                        }
                        item {
                            TrackItem()
                        }
                        item {
                            TrackItem()
                        }
                        item {
                            TrackItem()
                        }
                        item {
                            TrackItem()
                        }
                        item {
                            TrackItem()
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
            4 -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    item {
                        PodcastCard()
                    }
                    item {
                        PodcastCard()
                    }
                    item {
                        PodcastCard()
                    }
                    item {
                        PodcastCard()
                    }
                    item {
                        PodcastCard()
                    }
                    item {
                        PodcastCard()
                    }
                }
            }
            5 -> {
                Box {
                    val state = rememberLazyListState()
                    LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp)) {
                        item {
                            EpisodeItem()
                        }
                        item {
                            EpisodeItem()
                        }
                        item {
                            EpisodeItem()
                        }
                        item {
                            EpisodeItem()
                        }
                        item {
                            EpisodeItem()
                        }
                        item {
                            EpisodeItem()
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
            6 -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    item {
                        PlaylistCard()
                    }
                    item {
                        PlaylistCard()
                    }
                    item {
                        PlaylistCard()
                    }
                    item {
                        PlaylistCard()
                    }
                    item {
                        PlaylistCard()
                    }
                    item {
                        PlaylistCard()
                    }
                }
            }
        }
    }
}