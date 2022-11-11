package pages.artist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.ArtistInfo
import components.CoverImage
import layouts.Flow

@Composable
internal fun InfoPanel(artistInfo: ArtistInfo) {
    Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        CoverImage(artistInfo.artist.cover, customShape = CircleShape, defaultImage = Icons.Filled.Face)
        Column(Modifier.height(200.dp)) {
            Text("Исполнитель")
            Text(artistInfo.artist.name, fontWeight = FontWeight.Bold, fontSize = 45.sp)
            if (artistInfo.similarArtists.isNotEmpty()) {
                Flow(horizontalSpacing = 5.dp, verticalSpacing = 5.dp) {
                    Text("Нравится слушателям: ")
                    artistInfo.similarArtists.forEach {
                        Text(it.name)
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Row {
                Button({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Слушать")
                }
                OutlinedButton({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("1543562")
                }
                OutlinedButton({}, shape = CircleShape) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                Spacer(Modifier.weight(1f))
                OutlinedButton({}, shape = AbsoluteRoundedCornerShape(20.dp)) {
                    Icon(
                        Icons.Filled.Radio,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Поток")
                }
                OutlinedButton({}, shape = CircleShape) {
                    Icon(
                        Icons.Filled.NotInterested,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        }
    }
}