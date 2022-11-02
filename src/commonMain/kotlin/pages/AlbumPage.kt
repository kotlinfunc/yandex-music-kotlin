package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.AsyncImage
import components.loadImageBitmap

@Composable
@Preview
fun AlbumPage() {
    Column {
        Row {
            AsyncImage(
                load = { loadImageBitmap("https://avatars.mds.yandex.net/get-music-misc/34161/img.5c64538de638960072f990b4/orig") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
            )
            Column {
                Row {
                    Text("Альбом")
                    Text("Популярно у слушателей")
                }
                Text("Название альбома", fontWeight = FontWeight.Bold, fontSize = 45.sp)
                Text("Исполнители")
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("2021")
                    Text("Жанр")
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
        Column {
            // Tracks go here
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Лейбл", fontSize = 13.sp, fontWeight = FontWeight.Light)
            Text("United Trust Of Sonic Preservation", fontSize = 13.sp)
            Text("BMG Rights Management (US)", fontSize = 13.sp)
            Text("Alchemy", fontSize = 13.sp)
        }
        Text("Новые альбомы жанра", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Другие альбомы исполнителя", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}