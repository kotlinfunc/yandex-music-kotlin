package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import navigation.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun PodcastCard(onLocationChange: (Location<*>) -> Unit = {}) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            AsyncImage(
                load = { loadImageBitmap("https://avatars.yandex.net/get-music-content/2424959/b28d033f.a.10572815-2/200x200") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(200.dp).height(200.dp)
            )
            Text("Название")
            Row(Modifier.width(200.dp), Arrangement.spacedBy(5.dp)) {
                Icon(Icons.Outlined.Favorite, contentDescription = null)
                Text("200", overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
        }
    }
}