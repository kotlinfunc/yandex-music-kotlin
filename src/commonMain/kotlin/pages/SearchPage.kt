package pages

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.Response
import api.models.Search
import api.search
import components.*
import navigation.Location

@Composable
fun SearchPage(query: String, onLocationChange: (Location<*>) -> Unit = {}) {
    var searchResult by remember { mutableStateOf<Response<Search>?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Исполнители", "Альбомы", "Треки", "Подкасты", "Выпуски", "Плейлисты")

    LaunchedEffect(query) {
        searchResult = search(query)
    }

    if (searchResult == null) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (searchResult?.error != null) {
        Text("Ошибка: ${searchResult?.error?.message}")
    } else {
        Column {
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
                            searchResult?.result?.artists?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Button(onClick = { selectedTab = 1 }) {
                                        Text("Смотреть всех")
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        ArtistCard(it) { onLocationChange(it) }
                                    }
                                }
                            }
                            searchResult?.result?.albums?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Button(onClick = { selectedTab = 2 }) {
                                        Text("Смотреть всех")
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        AlbumCard(it) { onLocationChange(it) }
                                    }
                                }
                            }
                            searchResult?.result?.tracks?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Button(onClick = { selectedTab = 3 }) {
                                        Text("Смотреть всех")
                                    }
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        TrackItem(it) { onLocationChange(it) }
                                    }
                                }
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Подкасты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Button(onClick = { selectedTab = 4 }) {
                                    Text("Смотреть всех")
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                PodcastCard() { onLocationChange(it) }
                                PodcastCard() { onLocationChange(it) }
                                PodcastCard() { onLocationChange(it) }
                                PodcastCard() { onLocationChange(it) }
                                PodcastCard() { onLocationChange(it) }
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Выпуски", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Button(onClick = { selectedTab = 5 }) {
                                    Text("Смотреть всех")
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                EpisodeItem() { onLocationChange(it) }
                                EpisodeItem() { onLocationChange(it) }
                                EpisodeItem() { onLocationChange(it) }
                                EpisodeItem() { onLocationChange(it) }
                                EpisodeItem() { onLocationChange(it) }
                            }
                            searchResult?.result?.playlists?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Button(onClick = { selectedTab = 6 }) {
                                        Text("Смотреть всех")
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.take(5).forEach {
                                        PlaylistCard(it) { onLocationChange(it) }
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
                1 -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(10.dp),
                    ) {
                        searchResult?.result?.artists?.results?.let {
                            items(it) {
                                ArtistCard(it) { onLocationChange(it) }
                            }
                        }
                    }
                }
                2 -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(10.dp),
                    ) {
                        searchResult?.result?.albums?.results?.let {
                            items(it) {
                                AlbumCard(it) { onLocationChange(it) }
                            }
                        }
                    }
                }
                3 -> {
                    Box(Modifier.fillMaxSize()) {
                        val state = rememberLazyListState()
                        LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp)) {
                            searchResult?.result?.tracks?.results?.let {
                                items(it) {
                                    TrackItem(it) { onLocationChange(it) }
                                }
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
                            PodcastCard() { onLocationChange(it) }
                        }
                        item {
                            PodcastCard() { onLocationChange(it) }
                        }
                        item {
                            PodcastCard() { onLocationChange(it) }
                        }
                        item {
                            PodcastCard() { onLocationChange(it) }
                        }
                        item {
                            PodcastCard() { onLocationChange(it) }
                        }
                        item {
                            PodcastCard() { onLocationChange(it) }
                        }
                    }
                }
                5 -> {
                    Box {
                        val state = rememberLazyListState()
                        LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp)) {
                            item {
                                EpisodeItem() { onLocationChange(it) }
                            }
                            item {
                                EpisodeItem() { onLocationChange(it) }
                            }
                            item {
                                EpisodeItem() { onLocationChange(it) }
                            }
                            item {
                                EpisodeItem() { onLocationChange(it) }
                            }
                            item {
                                EpisodeItem() { onLocationChange(it) }
                            }
                            item {
                                EpisodeItem() { onLocationChange(it) }
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
                        searchResult?.result?.playlists?.results?.let {
                            items(it) {
                                PlaylistCard(it) { onLocationChange(it) }
                            }
                        }
                    }
                }
            }
        }
    }
}