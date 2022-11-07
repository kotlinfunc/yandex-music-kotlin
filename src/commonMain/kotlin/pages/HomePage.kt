package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import api.getChart
import api.getFeed
import api.getGenres
import api.models.*
import components.AlbumCard
import components.PlaylistCard
import components.TrackItem
import layouts.Flow
import layouts.TruncatedRow
import navigation.*

@Composable
@Preview
fun HomePage(onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Новые релизы", "Чарт", "Настроения и жанры")

    var feedResponse by remember { mutableStateOf<Response<Feed>?>(null) }
    LaunchedEffect(true) {
        feedResponse = getFeed()
    }

    var chartResponse by remember { mutableStateOf<Response<Chart>?>(null) }
    LaunchedEffect(ChartScope.RUSSIA) {
        chartResponse = getChart(ChartScope.RUSSIA)
    }

    var genresResponse by remember { mutableStateOf<Response<List<Genre>>?>(null) }
    LaunchedEffect(true) {
        genresResponse = getGenres()
    }

    Column {
        Text("Главное", fontWeight = FontWeight.Bold, fontSize = 45.sp)
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
                val newAlbums = feedResponse?.result?.days?.get(0)?.events?.first { event -> event is Feed.NewAlbumsOfFavoriteGenre }?.let {
                    (it as Feed.NewAlbumsOfFavoriteGenre).albums
                }
                val chartInfo = chartResponse?.result

                if (newAlbums == null || chartInfo == null) {
                    Column(Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else {
                    val stateVertical = rememberScrollState(0)
                    Box(Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical), Arrangement.spacedBy(10.dp)) {
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Text("Новые релизы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                TextButton({ selectedTab = 0 }) {
                                    Text("Смотреть все")
                                }
                            }
                            TruncatedRow(horizontalSpacing = 10.dp) {
                                newAlbums.take(5).forEach {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }) { onLocationChange(it) }
                                }
                            }

                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Column {
                                    Text(chartInfo.chart.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Text(chartInfo.title)
                                }
                                TextButton({ selectedTab = 1 }) {
                                    Text("Смотреть все")
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                chartInfo.chart.tracks?.take(10)?.let {
                                    it.forEach {
                                        TrackItem(it.track, onClick = { onInfoRequest(TrackInfo(it.id)) }) { onLocationChange(it) }
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
            }
            1 -> {
                val newAlbums = feedResponse?.result?.days?.get(0)?.events?.first { event -> event is Feed.NewAlbumsOfFavoriteGenre }?.let {
                    (it as Feed.NewAlbumsOfFavoriteGenre).albums
                }

                if (feedResponse == null) {
                    Column(Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else if (newAlbums != null) {
                    Column {
                        Text("Новые треки, альбомы и сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(newAlbums) {
                                AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }) { onLocationChange(it) }
                            }
                        }
                    }
                }
            }
            2 -> {
                val chartInfo = chartResponse?.result
                if (chartInfo == null) {
                    Column(Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else {
                    val stateVertical = rememberScrollState(0)

                    Box(Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                            Text(chartInfo.chart.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text(chartInfo.title)
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                chartInfo.chart.tracks?.let {
                                    it.forEach {
                                        TrackItem(it.track, onClick = { onInfoRequest(TrackInfo(it.id)) }) { onLocationChange(it) }
                                    }
                                }
                            }
                            chartInfo.chart.similarPlaylists?.let {
                                Text("Плейлисты с другими чартами", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                    it.forEach {
                                        PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }) { onLocationChange(it) }
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
            }
            3 -> {
                val genres = genresResponse?.result
                if (genres == null) {
                    Column(Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else {
                    val stateVertical = rememberScrollState(0)

                    Box(Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                            Text("Жанры", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                genres.forEach {
                                    Column {
                                        Text(it.title, fontWeight = FontWeight.Bold)
                                        it.subGenres?.forEach {
                                            Text(it.title)
                                        }
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
            }
        }
    }
}