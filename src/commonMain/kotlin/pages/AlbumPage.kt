package pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getAlbumWithTracks
import api.getArtistDirectAlbums
import api.getMetaTagAlbums
import api.models.Album
import api.models.ArtistAlbums
import api.models.MetaTag
import api.models.Response
import components.AlbumCard
import components.AsyncImage
import components.SimpleTrackItem
import components.loadImageBitmap
import layouts.Flow
import layouts.TruncatedRow
import navigation.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumPage(id: Long, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var albumResponse by remember { mutableStateOf<Response<Album>?>(null) }
    var genreAlbumsResponse by remember { mutableStateOf<Response<MetaTag>?>(null) }
    var otherAlbumsResponse by remember { mutableStateOf<Response<ArtistAlbums>?>(null) }
    val stateVertical = rememberScrollState(0)

    LaunchedEffect(id) {
        val albumWithTracks = getAlbumWithTracks(id)
        albumWithTracks.result?.artists?.get(0)?.let {
            otherAlbumsResponse = getArtistDirectAlbums(it.id)
        }
        albumWithTracks.result?.metaTagId?.let {
            genreAlbumsResponse = getMetaTagAlbums(it, pageSize = 10)
        }
        albumResponse = albumWithTracks
    }

    if (albumResponse == null) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (albumResponse?.error != null) {
        Text("Ошибка: ${albumResponse?.error?.message}")
    } else {
        albumResponse?.result?.let { album ->
            Column {
                Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    AsyncImage(
                        load = { loadImageBitmap("https://" + album.coverUri?.replace("%%", "200x200")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.size(200.dp)
                    )
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Альбом")
                            if (album.veryImportant == true) {
                                Text("Популярно у слушателей")
                            }
                        }
                        Text(album.title, fontWeight = FontWeight.Bold, fontSize = 45.sp)
                        Flow(horizontalSpacing = 10.dp, verticalSpacing = 5.dp) {
                            Text("Исполнитель: ")
                            album.artists?.map { artist ->
                                Text(
                                    artist.name,
                                    Modifier.onClick { onLocationChange(ArtistLocation(artist.id)) },
                                    overflow = TextOverflow.Ellipsis, maxLines = 1) }
                                ?: Text("Неизвестен")
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(album.year?.toString() ?: "Неизвестен")
                            Text(album.genre ?: "Неизвестный жанр")
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
                            IconButton({}) {
                                Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                            OutlinedButton({}, shape = CircleShape) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
                        }
                    }
                }
                Box(Modifier.fillMaxSize()) {
                    Column(Modifier.padding(10.dp).fillMaxWidth().verticalScroll(stateVertical)) {
                        album.volumes?.let {
                            Column {
                                if (it.size == 1) {
                                    it[0].forEachIndexed() { idx, track ->
                                        SimpleTrackItem(track, idx + 1, onClick = { onInfoRequest(TrackInfo(track.id)) })
                                    }
                                } else {
                                    it.forEachIndexed { volumeIdx, volume ->
                                        Text("Диск №${volumeIdx + 1}")
                                        volume.forEachIndexed() { idx, track ->
                                            SimpleTrackItem(track, idx + 1, onClick = { onInfoRequest(TrackInfo(track.id)) })
                                        }
                                    }
                                }
                            }
                        }
                        album.labels?.let {
                            if (it.isNotEmpty()) {
                                Text(buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                                        append("Лейбл ")
                                    }
                                    append(it.joinToString(", "))
                                }, fontSize = 13.sp)
                            }
                        }
                        album.duplicates?.let {
                            Text("Другие версии альбома", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                                it.forEach {
                                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                }
                            }
                        }
                        genreAlbumsResponse?.result?.let {
                            if (it.albums?.isNotEmpty() == true) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                    Text("Новые альбомы жанра «${it.title.title}»", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    TextButton(onClick = { onLocationChange(TagLocation(it.id)) }) {
                                        Text("Смотреть все")
                                    }
                                }
                                TruncatedRow(horizontalSpacing = 10.dp) {
                                    it.albums.take(10).forEach {
                                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                                    }
                                }
                            }
                        }
                        otherAlbumsResponse?.result?.albums?.let {
                            if (it.isNotEmpty()) {
                                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                    Text("Другие альбомы исполнителя", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    TextButton(onClick = { onLocationChange(ArtistLocation(album.artists!![0].id)) }) {
                                        Text("Смотреть все")
                                    }
                                }
                                TruncatedRow(horizontalSpacing = 10.dp) {
                                    it.filter { otherAlbum -> otherAlbum.id != album.id }.take(5).forEach {
                                        AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
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