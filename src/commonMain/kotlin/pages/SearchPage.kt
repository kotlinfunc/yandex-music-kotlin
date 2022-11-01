package pages

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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

@Composable
fun SearchPage(query: String) {
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
                            searchResult?.result?.artists?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Исполнители")
                                    Button(onClick = { selectedTab = 1 }) {
                                        Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        ArtistCard(it)
                                    }
                                }
                            }
                            searchResult?.result?.albums?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Альбомы")
                                    Button(onClick = { selectedTab = 2 }) {
                                        Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        AlbumCard(it)
                                    }
                                }
                            }
                            searchResult?.result?.tracks?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Треки")
                                    Button(onClick = { selectedTab = 3 }) {
                                        Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.forEach {
                                        TrackItem(it)
                                    }
                                }
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
                            searchResult?.result?.playlists?.let {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Плейлисты")
                                    Button(onClick = { selectedTab = 6 }) {
                                        Text("Смотреть всех", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    it.results.take(5).forEach {
                                        PlaylistCard(it)
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
                        searchResult?.result?.artists?.results?.forEach {
                            item {
                                ArtistCard(it)
                            }
                        }
                    }
                }
                2 -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(10.dp),
                    ) {
                        searchResult?.result?.albums?.results?.forEach {
                            item {
                                AlbumCard(it)
                            }
                        }
                    }
                }
                3 -> {
                    Box(Modifier.fillMaxSize()) {
                        val state = rememberLazyListState()
                        LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp)) {
                            searchResult?.result?.tracks?.results?.forEach {
                                item {
                                    TrackItem(it)
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
                        searchResult?.result?.playlists?.results?.forEach {
                            item {
                                PlaylistCard(it)
                            }
                        }
                    }
                }
            }
        }
    }
}