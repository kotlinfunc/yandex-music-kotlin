package pages

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getMetaTag
import api.models.MetaTag
import api.models.PlaylistId
import api.models.Response
import components.AlbumCard
import components.ArtistCard
import components.PlaylistCard
import layouts.Flow
import navigation.*

@Composable
fun MetaTagPage(tag: String, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var metaTagResponse by remember { mutableStateOf<Response<MetaTag>?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Обзор", "Альбомы", "Исполнители", "Плейлисты")

    LaunchedEffect(tag) {
        metaTagResponse = getMetaTag(tag)
    }

    if (metaTagResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        metaTagResponse?.result?.let {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(it.title.title, fontWeight = FontWeight.Bold, fontSize = 45.sp)
                    Spacer(Modifier.weight(1f))
                    Button({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                        Icon(
                            Icons.Filled.Radio,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Поток")
                        IconButton({}) {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                        }
                    }
                }
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
                                if (it.albums?.isNotEmpty() == true) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text(
                                            "${it.albumsSortByValues!!.first { it.active }.title} альбомы",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                        TextButton(onClick = { selectedTab = 1 }) {
                                            Text("Все")
                                        }
                                    }
                                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                        it.albums.take(10).forEach {
                                            AlbumCard(
                                                it,
                                                onClick = { onInfoRequest(AlbumInfo(it.id)) },
                                                onLocationChange = onLocationChange
                                            )
                                        }
                                    }
                                }
                                if (it.artists?.isNotEmpty() == true) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text("Популярные исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        TextButton(onClick = { selectedTab = 2 }) {
                                            Text("Все")
                                        }
                                    }
                                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                        it.artists.take(10).forEach {
                                            ArtistCard(
                                                it,
                                                onClick = { onInfoRequest(ArtistInfo(it.id)) },
                                                onLocationChange = onLocationChange
                                            )
                                        }
                                    }
                                }
                                if (it.playlists?.isNotEmpty() == true) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                        Text(
                                            "${it.playlistsSortByValues!!.first { it.active }.title} плейлисты",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                        TextButton(onClick = { selectedTab = 3 }) {
                                            Text("Все")
                                        }
                                    }
                                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                        it.playlists.take(10).forEach {
                                            PlaylistCard(
                                                it,
                                                onClick = {
                                                    onInfoRequest(
                                                        PlaylistInfo(
                                                            PlaylistId(
                                                                it.uid,
                                                                it.kind
                                                            )
                                                        )
                                                    )
                                                },
                                                onLocationChange = onLocationChange
                                            )
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
                        if (it.albums?.isNotEmpty() == true) {
                            Text("${it.albumsSortByValues!!.first { it.active }.title} альбомы", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 200.dp),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.albums) {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    2 -> {
                        if (it.artists?.isNotEmpty() == true) {
                            Text("Популярные исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 200.dp),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.artists) {
                                    ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    3 -> {
                        if (it.playlists?.isNotEmpty() == true) {
                            Text("${it.playlistsSortByValues!!.first { it.active }.title} плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 200.dp),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it.playlists) {
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