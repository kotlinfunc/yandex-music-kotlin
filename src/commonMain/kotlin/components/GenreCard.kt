package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import api.models.Genre
import util.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreCard(genre: Genre) {
    var subGenresExpanded by remember { mutableStateOf(false) }

    Card {
        Row(Modifier.fillMaxWidth().wrapContentSize(Alignment.TopStart), verticalAlignment = Alignment.CenterVertically) {
            genre.radioIcon?.let {
                AsyncImage(
                    load = { loadImageBitmap("https://" + it.imageUrl.replace("%%", "50x50")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.background(it.backgroundColor.toColor(), CircleShape).width(50.dp).height(50.dp)
                )
            }
            Text(genre.title, Modifier.weight(1f))

            genre.subGenres?.let {
                IconButton(onClick = { subGenresExpanded = !subGenresExpanded }) {
                    Icon(Icons.Default.MoreVert, null)
                }
                DropdownMenu(
                    expanded = subGenresExpanded,
                    onDismissRequest = { subGenresExpanded = false }
                ) {
                    it.forEach {
                        DropdownMenuItem({ }) {
                            Row {
                                Icon(Icons.Filled.PlayCircle, null)
                                Text(it.title)
                            }
                        }
                    }
                }
            }
        }
    }
}