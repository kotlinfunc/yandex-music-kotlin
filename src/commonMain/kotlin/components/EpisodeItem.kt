package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import navigation.Location

@Composable
@Preview
fun EpisodeItem(onLocationChange: (Location<*>) -> Unit = {}) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            load = { loadImageBitmap("https://avatars.yandex.net/get-music-content/2424959/b28d033f.a.10572815-2/200x200") },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(50.dp).height(50.dp)
        )
        Spacer(Modifier.size(5.dp))
        Column {
            Text("Название")
            Text("Подкаст")
        }
        Spacer(Modifier.fillMaxWidth())
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Spacer(Modifier.size(5.dp))
        Button(onClick = {}) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Spacer(Modifier.size(5.dp))
        Text("3:03")
    }
}