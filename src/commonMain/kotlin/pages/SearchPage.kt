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
import api.models.PlaylistId
import api.models.Response
import api.models.Search
import api.search
import components.*
import layouts.TruncatedRow
import navigation.*

@Composable
fun SearchPage(query: String, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
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

            searchResult?.result?.let {
                when(selectedTab) {
                    0 -> {
                        val stateVertical = rememberScrollState(0)

                        Box(Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                                it.artists?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 1 }) {
                                            Text("Смотреть всех")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        it.results.forEach {
                                            ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                it.albums?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        it.results.forEach {
                                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                it.tracks?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 3 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        it.results.forEach {
                                            TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                it.podcasts?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Подкасты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 4 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        it.results.forEach {
                                            PodcastCard(it, onClick = { onInfoRequest(PodcastInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                it.podcastEpisodes?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Выпуски", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 5 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        it.results.take(5).forEach {
                                            EpisodeItem(it, onClick = { onInfoRequest(EpisodeInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                it.playlists?.let {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 6 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        it.results.take(5).forEach {
                                            PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
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
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                           it.artists?.results?.let {
                                items(it) {
                                    ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    2 -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            it.albums?.results?.let {
                                items(it) {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    3 -> {
                        Box(Modifier.fillMaxSize()) {
                            val state = rememberLazyListState()
                            LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                it.tracks?.results?.let {
                                    items(it) {
                                        TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
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
                        it.podcasts?.results?.let {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 200.dp),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it) {
                                    PodcastCard(it, onClick = { onInfoRequest(PodcastInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    5 -> {
                        it.podcastEpisodes?.let {
                            Box {
                                val state = rememberLazyListState()
                                LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(it.results) {
                                        EpisodeItem(it, onClick = { onInfoRequest(EpisodeInfo(it.id)) }, onLocationChange = onLocationChange)
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
                    }
                    6 -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            it.playlists?.results?.let {
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
}