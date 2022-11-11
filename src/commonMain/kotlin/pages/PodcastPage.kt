package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import androidx.compose.ui.window.Dialog
import api.getPodcastWithEpisodes
import api.models.Podcast
import api.models.Response
import components.AsyncImage
import components.PodcastEpisodeItem
import components.loadImageBitmap
import navigation.EpisodeInfo
import navigation.Info
import navigation.Location

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun PodcastPage(id: Long, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var podcastResponse by remember { mutableStateOf<Response<Podcast>?>(null) }
    val titles = listOf("О подкасте", "Выпуски")

    LaunchedEffect(id) {
        loading = true
        podcastResponse = getPodcastWithEpisodes(id)
        loading = false
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
            var selectedTab by remember { mutableStateOf(0) }
            var showCoverDialog by remember { mutableStateOf(false) }

            Column {
                Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    AsyncImage(
                        load = { loadImageBitmap("https://" + it.coverUri.replace("%%", "200x200")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.onClick { showCoverDialog = true }.size(200.dp)
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
                                //Text("Последние выпуски", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
                                            PodcastEpisodeItem(it, onClick = { onInfoRequest(EpisodeInfo(it.id)) })
                                        }
                                    } else {
                                        it.forEachIndexed { idx, episodes ->
                                            Text("Сезон ${it.size - idx}")
                                            episodes.forEach {
                                                PodcastEpisodeItem(it, onClick = { onInfoRequest(EpisodeInfo(it.id)) })
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

            if (showCoverDialog) {
                Dialog({ showCoverDialog = false }, title = "${it.title}: обложка") {
                    AsyncImage(
                        load = { loadImageBitmap("https://" + it.coverUri.replace("%%", "1000x1000")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}