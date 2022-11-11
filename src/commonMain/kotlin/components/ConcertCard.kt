package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import api.models.Concert
import util.date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertCard(concert: Concert, onClick: () -> Unit = {}) {
    Card {
      Column {
          AsyncImage(
              load = { loadImageBitmap(concert.images[0].replace("%%", "278x180")) },
              painterFor = { remember { BitmapPainter(it) } },
              contentDescription = "",
              contentScale = ContentScale.FillBounds,
              modifier = Modifier.clickable(onClick = onClick).defaultMinSize(278.dp, 180.dp).width(278.dp).height(180.dp)
          )
          Row(Modifier.width(278.dp), Arrangement.spacedBy(10.dp)) {
              Text(concert.dateTime.date())
              Column {
                  Text(concert.concertTitle, fontWeight = FontWeight.Bold)
                  Text("${concert.city}: ${concert.address} (${concert.place})")
                  Text(concert.contentRating)
              }
          }
      }
    }
}