package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getPodcastWithEpisodes
import api.models.Podcast
import api.models.Response
import components.AsyncImage
import components.PodcastEpisodeItem
import components.loadImageBitmap
import navigation.Location

@Composable
@Preview
fun PodcastPage(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var podcastResponse by remember { mutableStateOf<Response<Podcast>?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("О подкасте", "Выпуски")

    LaunchedEffect(id) {
        podcastResponse = getPodcastWithEpisodes(id)
    }

    if (podcastResponse == null) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (podcastResponse?.error != null) {
        Text("Ошибка: ${podcastResponse?.error?.message}")
    } else {
        podcastResponse?.result?.let {
            Column {
                Row {
                    AsyncImage(
                        load = { loadImageBitmap("https://" + it.coverUri.replace("%%", "200x200")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.size(200.dp)
                    )
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Подкаст")
                            if (it.veryImportant) {
                                Text("Популярно у слушателей")
                            }
                        }
                        Text(it.title, fontWeight = FontWeight.Bold, fontSize = 45.sp)
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
                            OutlinedButton({}) {
                                Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(it.likesCount?.toString() ?: "0")
                            }
                            IconButton({}) {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                            }
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
                            Column(Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                                it.description?.let {
                                    Text(it)
                                }
                                //Text("Чтецы")
                                //Text("Продолжительность")
                                //Text("Возрастное ограничение")
                                Text("Последние выпуски", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                //Text("Популярно в \"Раздел подкаста\"", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                            VerticalScrollbar(
                                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                                adapter = rememberScrollbarAdapter(stateVertical)
                            )
                        }
                    }
                    1 -> {
                        it.volumes?.let {
                            val stateVertical = rememberScrollState(0)

                            Box(Modifier.fillMaxSize()) {
                                Column(Modifier.fillMaxWidth().verticalScroll(stateVertical)) {
                                    if (it.size == 1) {
                                        it[0].forEach {
                                            PodcastEpisodeItem(it)
                                        }
                                    } else {
                                        it.forEachIndexed { idx, episodes ->
                                            Text("Сезон ${idx + 1}")
                                            episodes.forEach {
                                                PodcastEpisodeItem(it)
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
}