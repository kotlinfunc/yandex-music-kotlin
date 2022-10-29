package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
@Preview
fun SearchPage() {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Исполнители", "Альбомы", "Треки", "Подкасты", "Эпизоды", "Плейлисты")

    Column {
        TabRow(selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }
    }
}