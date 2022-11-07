package panels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getPodcastWithEpisodes
import api.models.Podcast
import api.models.Response
import components.PodcastEpisodeItem
import navigation.Location
import navigation.PodcastLocation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PodcastInfoPanel(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var podcastResponse by remember { mutableStateOf<Response<Podcast>?>(null) }

    LaunchedEffect(id) {
        podcastResponse = getPodcastWithEpisodes(id)
    }

    if (podcastResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (podcastResponse?.error != null) {
        Text("Ошибка: ${podcastResponse?.error?.message}")
    } else {
        podcastResponse?.result?.let {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Подкаст")
                    Text(it.title, Modifier.onClick { onLocationChange(PodcastLocation(it.id)) }, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    IconButton({}) {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
                it.shortDescription?.let {
                    Text(it)
                }
                it.volumes?.let {
                    if (it.size == 1) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            it[0].forEach {
                                PodcastEpisodeItem(it)
                            }
                        }
                    } else {
                        it.forEachIndexed { idx, episodes ->
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Сезон ${it.size - idx}")
                                episodes.forEach {
                                    PodcastEpisodeItem(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}