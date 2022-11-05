package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import api.models.Video

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoCard(video: Video) {
    Card(Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
        Column {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    load = { loadImageBitmap(video.cover.replace("%%", "200x112")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.defaultMinSize(200.dp, 112.dp).width(200.dp).height(112.dp)
                )
                Icon(Icons.Filled.PlayArrow, null, modifier = Modifier.size(ButtonDefaults.IconSize))
            }
            Text(video.title, Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}