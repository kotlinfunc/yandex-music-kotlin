package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getArtistBriefInfo
import api.models.ArtistInfo
import api.models.Link
import api.models.Response
import components.*
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Facebook
import compose.icons.fontawesomeicons.brands.Twitter
import compose.icons.fontawesomeicons.brands.Youtube
import navigation.Location
import util.openInBrowser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ArtistPage(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var artistResponse by remember { mutableStateOf<Response<ArtistInfo>?>(null) }

    LaunchedEffect(id) {
        artistResponse = getArtistBriefInfo(id)
    }

    if (artistResponse == null) {
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
                Row {
                    CoverImage(artistInfo.artist.cover, Icons.Filled.Face)
                    Column(Modifier.height(200.dp)) {
                        Text("Исполнитель")
                        Text(artistInfo.artist.name, fontWeight = FontWeight.Bold, fontSize = 45.sp)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text("Нравится слушателям: ")
                            artistInfo.similarArtists.forEach {
                                Text(it.name)
                            }
                        }
                        Row {
                            Button({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Слушать")
                            }
                            OutlinedButton({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                                Icon(
                                    Icons.Outlined.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("1543562")
                            }
                            OutlinedButton({}, shape = CircleShape) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                            OutlinedButton({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                                Icon(
                                    Icons.Filled.Radio,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Поток")
                            }
                            OutlinedButton({}, shape = CircleShape) {
                                Icon(
                                    Icons.Filled.NotInterested,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                    }
                }

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
                    0 -> {
                        val stateVertical = rememberScrollState(0)
                        Box(Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                                if (artistInfo.popularTracks.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Популярные треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 1 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    artistInfo.popularTracks.forEach {
                                        TrackItem(it) { onLocationChange(it) }
                                    }
                                }
                                if (artistInfo.albums.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Популярные альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Row {
                                        artistInfo.albums.take(5).forEach {
                                            AlbumCard(it) { onLocationChange(it) }
                                        }
                                    }
                                }
                                if (artistInfo.alsoAlbums.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Популярные сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Row {
                                        artistInfo.alsoAlbums.take(5).forEach {
                                            AlbumCard(it) { onLocationChange(it) }
                                        }
                                    }
                                }
                                if (artistInfo.playlists?.isNotEmpty() == true) {
                                    Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Row {
                                        artistInfo.playlists.take(5).forEach {
                                            PlaylistCard(it) { onLocationChange(it) }
                                        }
                                    }
                                }
                                if (artistInfo.videos.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Клипы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 3 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Row {
                                        artistInfo.videos.take(5).forEach {
                                            VideoCard(it)
                                        }
                                    }
                                }
                                if (artistInfo.concerts.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 4 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Row {
                                        artistInfo.concerts.take(5).forEach {
                                            ConcertCard(it)
                                        }
                                    }
                                }
                                if (artistInfo.similarArtists.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 5 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    Row {
                                        artistInfo.similarArtists.take(5).forEach {
                                            ArtistCard(it) { onLocationChange(it) }
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
                        val state = rememberLazyListState()
                        Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Box(Modifier.fillMaxSize()) {
                            LazyColumn(Modifier.fillMaxSize(), state, contentPadding = PaddingValues(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                itemsIndexed(artistInfo.popularTracks) { number, track ->
                                    SimpleTrackItem(track, number + 1) { onLocationChange(it) }
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
                    2 -> {
                        Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(artistInfo.albums) {
                                AlbumCard(it) { onLocationChange(it) }
                            }
                        }
                    }
                    3 -> {
                        Text("Клипы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 200.dp),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(artistInfo.videos) {
                                VideoCard(it)
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
                                ConcertCard(it)
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
                                ArtistCard(it) { onLocationChange(it) }
                            }
                        }
                    }
                    6 -> {
                        val stateVertical = rememberScrollState(0)
                        Box(Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                                if (artistInfo.allCovers.size > 1) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                        artistInfo.allCovers.forEach {
                                            AsyncImage(
                                                load = { loadImageBitmap("https://" + it.uri!!.replace("%%", "1000x1000")) },
                                                painterFor = { remember { BitmapPainter(it) } },
                                                contentDescription = "",
                                                contentScale = ContentScale.FillWidth,
                                                modifier = Modifier.width(290.dp).height(185.dp)
                                            )
                                        }
                                    }
                                }
                                artistInfo.stats?.let {
                                    Text("Слушателей за предыдущий месяц: ${it.lastMonthListeners}")
                                }
                                Row(Modifier.fillMaxWidth()) {
                                    artistInfo.artist.description?.let {
                                        Card(Modifier.fillMaxWidth(0.5f)) {
                                            Column {
                                                Text("Об исполнителе", fontWeight = FontWeight.Bold)
                                                Text(it.text)
                                            }
                                        }
                                    }
                                    artistInfo.artist.links?.let {
                                        Card(Modifier.fillMaxWidth(0.5f)) {
                                            Column {
                                                it.forEach {
                                                    OutlinedButton({ openInBrowser(it.href) }) {
                                                        Icon(
                                                            if (it.type == Link.Type.SOCIAL)
                                                                when (it.socialNetwork!!) {
                                                                    Link.SocialNetwork.FACEBOOK -> FontAwesomeIcons.Brands.Facebook
                                                                    Link.SocialNetwork.TWITTER -> FontAwesomeIcons.Brands.Twitter
                                                                    Link.SocialNetwork.YOUTUBE -> FontAwesomeIcons.Brands.Youtube
                                                                }
                                                            else Icons.Filled.Language,
                                                            contentDescription = null,
                                                            modifier = Modifier.size(ButtonDefaults.IconSize)
                                                        )
                                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                                        Text(it.title)
                                                    }
                                                }
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
}