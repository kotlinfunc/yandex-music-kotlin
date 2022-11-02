package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.AsyncImage
import components.loadImageBitmap
import navigation.Location

@Composable
@Preview
fun PlaylistPage(id: String, onLocationChange: (Location<*>) -> Unit = {}) {
    var searchText by remember { mutableStateOf("") }

    Column(Modifier.padding(5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                load = { loadImageBitmap("https://avatars.mds.yandex.net/get-music-misc/34161/img.5c64538de638960072f990b4/orig") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
            )
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Плейлист")
                    Text("Продолжительность")
                }
                Text("Название плейлиста", fontWeight = FontWeight.Bold, fontSize = 45.sp)
                Text("Описание")
                Row {
                    Text("Владелец")
                    Text("Дата последнего обновления")
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
        TextField(searchText, { searchText = it },
            Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                IconButton({ searchText = "" }, enabled = searchText.isNotBlank())
                { Icon(Icons.Filled.Clear, contentDescription = null) }
            },
            placeholder = { Text("Поиск") }, singleLine = true)
        Column {
            // Tracks go here
        }
        Text("Похожие плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}