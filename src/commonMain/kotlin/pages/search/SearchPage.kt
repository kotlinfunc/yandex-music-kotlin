package pages.search

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import api.models.PlaylistId
import api.models.Response
import api.models.Search
import api.models.SearchType
import api.search
import components.*
import navigation.*

@Composable
fun SearchPage(query: String, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var searchResult by remember { mutableStateOf<Response<Search>?>(null) }

    LaunchedEffect(query) {
        loading = true
        searchResult = search(query)
        loading = false
    }

    if (loading) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (searchResult?.error != null) {
        Text("Ошибка: ${searchResult?.error?.message}")
    } else {
        searchResult?.result?.let { overviewResult ->
            var selectedTab by remember { mutableStateOf(0) }
            val titles = listOf("Всё",
                "Исполнители: ${overviewResult.artists?.total ?: 0}",
                "Альбомы: ${overviewResult.albums?.total ?: 0}",
                "Треки: ${overviewResult.tracks?.total ?: 0}",
                "Подкасты: ${overviewResult.podcasts?.total ?: 0}",
                "Выпуски: ${overviewResult.podcastEpisodes?.total ?: 0}",
                "Плейлисты: ${overviewResult.playlists?.total ?: 0}")

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ScrollableTabRow(selectedTab) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }

                when(selectedTab) {
                    0 -> Overview(overviewResult, onInfoRequest, onLocationChange) { selectedTab = it }
                    1 -> if (overviewResult.artists != null) {
                        Part(query, 50, SearchType.ARTIST, Search::artists) {
                            LazyVerticalGrid(GridCells.Adaptive(minSize = 200.dp), Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.results) {
                                    ArtistCard(
                                        it,
                                        onClick = { onInfoRequest(ArtistInfo(it.id)) },
                                        onLocationChange = onLocationChange
                                    )
                                }
                            }
                        }
                    }
                    2 -> if (overviewResult.albums != null) {
                        Part(query, 50, SearchType.ALBUM, Search::albums) {
                            LazyVerticalGrid(GridCells.Adaptive(minSize = 200.dp), Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.results) {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    3 -> if (overviewResult.tracks != null) {
                        Part(query, 100, SearchType.TRACK, Search::tracks) {
                            Box(Modifier.weight(1f)) {
                                val state = rememberLazyListState()
                                LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(it.results) {
                                        TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
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
                    4 -> if (overviewResult.podcasts != null) {
                        Part(query, 50, SearchType.PODCAST, Search::podcasts) {
                            LazyVerticalGrid(
                                GridCells.Adaptive(minSize = 200.dp),
                                Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.results) {
                                    PodcastCard(it, onClick = { onInfoRequest(PodcastInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    5 -> if (overviewResult.podcastEpisodes != null) {
                        Part(query, 100, SearchType.PODCAST_EPISODE, Search::podcastEpisodes) {
                            Box(Modifier.weight(1f)) {
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
                    6 -> if (overviewResult.podcastEpisodes != null) {
                        Part(query, 100, SearchType.PLAYLIST, Search::playlists) {
                            LazyVerticalGrid(GridCells.Adaptive(minSize = 200.dp), Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.results) {
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