package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun AlbumCard() {
    Column {
        AsyncImage(
            load = { loadImageBitmap("https://avatars.yandex.net/get-music-content/6447985/d28af831.a.4474749-2/200x200") },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(200.dp).height(200.dp)
        )
        Text("Название")
        Text("Исполнитель")
        Row {
            Text("2022")
            Text("сингл")
            Text("делюкс")
        }
    }
}