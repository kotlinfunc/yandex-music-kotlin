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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getAlbumWithTracks
import api.models.Album
import api.models.Response
import components.AlbumCard
import components.AsyncImage
import components.SimpleTrackItem
import components.loadImageBitmap
import navigation.ArtistLocation
import navigation.Location

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumPage(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var albumResponse by remember { mutableStateOf<Response<Album>?>(null) }
    val stateVertical = rememberScrollState(0)

    LaunchedEffect(id) {
        albumResponse = getAlbumWithTracks(id)
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
                Row {
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
                        Row {
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
                    Column(Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                        album.volumes?.let {
                            Column {
                                if (it.size == 1) {
                                    it[0].forEachIndexed() { idx, track ->
                                        SimpleTrackItem(track, idx + 1)
                                    }
                                } else {
                                    it.forEachIndexed { volumeIdx, volume ->
                                        Text("Диск №${volumeIdx + 1}")
                                        volume.forEachIndexed() { idx, track ->
                                            SimpleTrackItem(track, idx + 1)
                                        }
                                    }
                                }
                            }
                        }
                        /*Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                                append("Лейбл: ")
                            }
                        }, fontSize = 13.sp)*/
                        album.duplicates?.let {
                            Text("Другие версии альбома", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Row(Modifier.wrapContentWidth(), Arrangement.spacedBy(10.dp)) {
                                it.forEach {
                                    AlbumCard(it) { onLocationChange(it) }
                                }
                            }
                        }
                        //Text("Новые альбомы жанра «${album.genre}»", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        //Text("Другие альбомы исполнителя", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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