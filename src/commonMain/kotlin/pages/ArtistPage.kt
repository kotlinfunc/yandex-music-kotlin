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
import api.models.*
import api.models.ArtistInfo
import components.*
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.*
import layouts.Flow
import layouts.TruncatedRow
import navigation.*
import util.openInBrowser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ArtistPage(id: Long, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
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
                Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    CoverImage(artistInfo.artist.cover, customShape = CircleShape, defaultImage = Icons.Filled.Face)
                    Column(Modifier.height(200.dp)) {
                        Text("Исполнитель")
                        Text(artistInfo.artist.name, fontWeight = FontWeight.Bold, fontSize = 45.sp)
                        if (artistInfo.similarArtists.isNotEmpty()) {
                            Flow(horizontalSpacing = 5.dp, verticalSpacing = 5.dp) {
                                Text("Нравится слушателям: ")
                                artistInfo.similarArtists.forEach {
                                    Text(it.name)
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
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
                            Spacer(Modifier.weight(1f))
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
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Популярные треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 1 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    artistInfo.popularTracks.take(5).forEach {
                                        TrackItem(it, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                                    }
                                }
                                if (artistInfo.albums.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Популярные альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.albums.take(5).forEach {
                                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                if (artistInfo.alsoAlbums.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Популярные сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.alsoAlbums.take(5).forEach {
                                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                if (artistInfo.playlists?.isNotEmpty() == true) {
                                    Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.playlists.take(5).forEach {
                                            PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                if (artistInfo.videos.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Клипы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 3 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.videos.take(5).forEach {
                                            VideoCard(it, onClick = { openInBrowser(it.embedUrl) })
                                        }
                                    }
                                }
                                if (artistInfo.concerts.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 4 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.concerts.take(5).forEach {
                                            ConcertCard(it, onClick = { openInBrowser(it.afishaUrl) })
                                        }
                                    }
                                }
                                if (artistInfo.similarArtists.isNotEmpty()) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 5 }) {
                                            Text("Смотреть все")
                                        }
                                    }
                                    TruncatedRow(horizontalSpacing = 10.dp) {
                                        artistInfo.similarArtists.take(5).forEach {
                                            ArtistCard(it, onClick = { onInfoRequest(navigation.ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
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
                    2 -> {
                        val stateVertical = rememberScrollState(0)
                        Box(Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                                if (artistInfo.albums.isNotEmpty()) {
                                    Text("Альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                        artistInfo.albums.forEach {
                                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                        }
                                    }
                                }
                                if (artistInfo.alsoAlbums.isNotEmpty()) {
                                    Text("Сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                        artistInfo.alsoAlbums.forEach {
                                            AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
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
                    6 -> {
                        val stateVertical = rememberScrollState(0)
                        Box(Modifier.fillMaxSize()) {
                            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                                if (artistInfo.allCovers.size > 1) {
                                    Flow(horizontalSpacing = 5.dp) {
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
                                                            if (it.type == Artist.Link.Type.SOCIAL)
                                                                when (it.socialNetwork!!) {
                                                                    Artist.Link.SocialNetwork.DISCORD -> FontAwesomeIcons.Brands.Discord
                                                                    Artist.Link.SocialNetwork.FACEBOOK -> FontAwesomeIcons.Brands.Facebook
                                                                    Artist.Link.SocialNetwork.TELEGRAM -> FontAwesomeIcons.Brands.Telegram
                                                                    Artist.Link.SocialNetwork.TIKTOK -> FontAwesomeIcons.Brands.Tiktok
                                                                    Artist.Link.SocialNetwork.TWITTER -> FontAwesomeIcons.Brands.Twitter
                                                                    Artist.Link.SocialNetwork.VK -> FontAwesomeIcons.Brands.Vk
                                                                    Artist.Link.SocialNetwork.YOUTUBE -> FontAwesomeIcons.Brands.Youtube
                                                                    Artist.Link.SocialNetwork.ZEN -> FontAwesomeIcons.Brands.Yandex
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