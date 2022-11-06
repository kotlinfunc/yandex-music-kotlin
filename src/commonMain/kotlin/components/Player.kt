package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import navigation.Location

@Composable
@Preview
fun Player(onLocationChange: (Location<*>) -> Unit = {}) {
    Row {
        IconButton({}) {
            Icon(
                Icons.Filled.SkipPrevious,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.SkipNext,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.List,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        AsyncImage(
            load = { loadImageBitmap("https://avatars.mds.yandex.net/get-music-misc/2413828/img.6140de34a3b5f56699eda275/orig") },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.defaultMinSize(50.dp, 50.dp).width(50.dp).height(50.dp)
        )
        IconButton({}) {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.Add,
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
        IconButton({}) {
            Icon(
                Icons.Filled.NotInterested,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton({}) {
            Icon(
                Icons.Filled.HighQuality,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.Shuffle,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.Repeat,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        IconButton({}) {
            Icon(
                Icons.Filled.VolumeUp,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}