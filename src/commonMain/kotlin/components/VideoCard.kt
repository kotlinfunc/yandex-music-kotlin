package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    Card {
        Column {
            AsyncImage(
                load = { loadImageBitmap(video.cover.replace("%%", "200x112")) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.defaultMinSize(200.dp, 112.dp).width(200.dp).height(112.dp)
            )
            Text(video.title, Modifier.width(200.dp),
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1)
        }
    }
}