package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Cover

@Composable
fun CoverImage(cover: Cover?, modifier: Modifier = Modifier, customShape: Shape = RoundedCornerShape(0), defaultImage: ImageVector? = null) {
    if (cover?.itemsUri != null) {
        Column(modifier.clip(customShape).height(200.dp)) {
            Row(Modifier.width(200.dp)) {
                cover.itemsUri.take(2).forEach { uri ->
                    AsyncImage(
                        load = { loadImageBitmap("https://" + uri.replace("%%", "100x100")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.requiredSize(100.dp)
                    )
                }
            }
            Row(Modifier.width(200.dp)) {
                cover.itemsUri.takeLast(2).forEach { uri ->
                    AsyncImage(
                        load = { loadImageBitmap("https://" + uri.replace("%%", "100x100")) },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.requiredSize(100.dp)
                    )
                }
            }
        }
    } else if (cover?.uri != null) {
        AsyncImage(
            load = { loadImageBitmap("https://" + cover.uri.replace("%%", "200x200")) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = modifier.clip(customShape).requiredSize(200.dp)
        )
    } else {
        if (defaultImage == null) {
            Box(modifier.requiredSize(200.dp, 200.dp))
        } else {
            Image(defaultImage, "", modifier = modifier.clip(customShape).requiredSize(200.dp))
        }
    }
}