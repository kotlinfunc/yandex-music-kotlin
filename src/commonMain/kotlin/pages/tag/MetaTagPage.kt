package pages.tag

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
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
import api.models.SearchType
import components.AlbumCard
import components.ArtistCard
import components.PlaylistCard
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
            Column(Modifier, Arrangement.spacedBy(10.dp), Alignment.CenterHorizontally) {
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
                    0 -> Overview(it, onInfoRequest, onLocationChange) { selectedTab = it }
                    1 -> if (it.albums?.isNotEmpty() == true) {
                        Part(tag, 50, SearchType.ALBUM,
                            it.albumsSortByValues!!.first { it.active }, { metaTag -> metaTag.albums }) {
                            LazyVerticalGrid(
                                GridCells.Adaptive(minSize = 200.dp),
                                Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it) {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    2 -> if (it.artists?.isNotEmpty() == true) {
                        Part(tag, 50, SearchType.ARTIST,
                            MetaTag.SortedBy("popular", "Популярные", true), { metaTag -> metaTag.artists }) {
                            LazyVerticalGrid(
                                GridCells.Adaptive(minSize = 200.dp),
                                Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(it) {
                                    ArtistCard(it, onClick = { onInfoRequest(ArtistInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                    }
                    3 -> if (it.playlists?.isNotEmpty() == true) {
                        Part(tag, 50, SearchType.PLAYLIST,
                            it.playlistsSortByValues!!.first { it.active }, { metaTag -> metaTag.playlists }) {
                            LazyVerticalGrid(
                                GridCells.Adaptive(minSize = 200.dp),
                                Modifier.weight(1f),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
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