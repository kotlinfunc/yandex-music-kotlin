package panels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getEpisode
import api.getEpisodeSupplement
import api.models.Episode
import api.models.EpisodeSupplement
import api.models.Response
import navigation.Location
import navigation.PodcastLocation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeInfoPanel(id: Long, onLocationChange: (Location<*>) -> Unit = {}) {
    var episodeResponse by remember { mutableStateOf<Response<List<Episode>>?>(null) }
    var episodeSupplement by remember { mutableStateOf<Response<EpisodeSupplement>?>(null) }

    LaunchedEffect(id) {
        episodeResponse = getEpisode(id)
        episodeSupplement = getEpisodeSupplement(id)
    }

    if (episodeResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (episodeResponse?.error != null) {
        Text("Ошибка: ${episodeResponse?.error?.message}")
    } else {
        episodeResponse?.result?.get(0)?.let {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Трек")
                    Text(it.title, Modifier.onClick { onLocationChange( PodcastLocation(it.albums.get(0).id)) },
                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
                Row {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.PlayCircleFilled,
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
                episodeSupplement?.result?.let {
                    Text(it.description)
                }
            }
        }
    }
}