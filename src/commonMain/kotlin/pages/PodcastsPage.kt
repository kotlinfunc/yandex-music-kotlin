package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import navigation.Location

@Composable
@Preview
fun PodcastsPage(onLocationChange: (Location<*>) -> Unit = {}) {
    Column {
        Text("Подкасты и книги", fontWeight = FontWeight.Bold, fontSize = 45.sp)
    }
}