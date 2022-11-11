package pages.album

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import api.models.Album
import components.AsyncImage
import components.loadImageBitmap
import layouts.Flow
import navigation.ArtistLocation
import navigation.Location

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun InfoPanel(album: Album, onLocationChange: (Location<*>) -> Unit = {}) {
    var showCoverDialog by remember { mutableStateOf(false) }

    Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        AsyncImage(
            load = { loadImageBitmap("https://" + album.coverUri?.replace("%%", "200x200")) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.onClick { showCoverDialog = true }.size(200.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
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

    if (showCoverDialog) {
        Dialog({ showCoverDialog = false }, title = "${album.title}: обложка") {
            AsyncImage(
                load = { loadImageBitmap("https://" + album.coverUri?.replace("%%", "1000x1000")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}