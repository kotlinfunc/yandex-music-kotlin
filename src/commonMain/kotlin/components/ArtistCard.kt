package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun ArtistCard() {
    Card {
        Column {
            AsyncImage(
                load = { loadImageBitmap("https://avatars.yandex.net/get-music-content/33216/c6d507c7.p.41075/200x200") },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(200.dp).height(200.dp)
            )
            Text("Исполнитель")
            Text("Жанр1, жанр2")
        }
    }
}