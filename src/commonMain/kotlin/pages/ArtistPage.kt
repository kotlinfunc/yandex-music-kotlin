package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.AsyncImage
import components.loadImageBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ArtistPage() {
    Column {
        Row {
            AsyncImage(
                load = { loadImageBitmap("https://avatars.mds.yandex.net/get-music-misc/34161/img.5c64538de638960072f990b4/orig") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 200.dp).width(200.dp).height(200.dp)
            )
            Column(Modifier.height(200.dp)) {
                Text("Исполнитель")
                Text("Имя исполнителя", fontWeight = FontWeight.Bold, fontSize = 45.sp)
                Text("Нравится слушателям: исполнитель 1, исполнитель 2")
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
                Text("Популярные треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Популярные альбомы и сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Плейлисты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            1 -> {
                Text("Треки", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            2 -> {
                Text("Исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                }
            }
            3 -> {
                Text("Исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            4 -> {
                Text("Концерты", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            5 -> {
                Text("Похожие исполнители", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                }
            }
            6 -> {
                Column {
                    Row {
                        // Images?
                    }
                    Row {
                        // Popularity
                    }
                    Row {
                        Card {
                            Column {
                                Text("Об исполнителе")
                                Text("Армяно-американская альтернатив-метал-группа, образованная в 1992 году в Лос-Анджелесе Сержем Танкяном и Дароном Малакяном под названием Soil, а в 1995 принявшая нынешнее название. Все участники группы имеют армянское происхождение. В период с 1998 по 2005 год группа выпустила пять студийных альбомов, каждый из которых стал платиновым. Было продано более 40 миллионов копий альбомов по всему миру. В 2006 году участники System of a Down решили временно приостановить совместную деятельность и заняться сольными проектами. 29 ноября 2010 года группа объявила о воссоединении и проведении европейского турне в 2011 году. Изначально группа должна была называться «Victims of the Down» - по стихотворению, написанному Дароном Малакяном..")
                            }
                        }
                        Card {
                            Column {
                                Text("Twitter")
                                Text("Youtube")
                                Text("Custom web-site")
                            }
                        }
                    }
                }
            }
        }
    }
}