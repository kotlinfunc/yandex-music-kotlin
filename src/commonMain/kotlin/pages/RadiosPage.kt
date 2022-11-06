package pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.getGenres
import api.models.Genre
import api.models.Response
import components.GenreCard
import navigation.Location

@Composable
fun RadiosPage(onLocationChange: (Location<*>) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Обзор", "Жанры", "Настроения", "Занятия", "Эпохи")

    var genresResponse by remember { mutableStateOf<Response<List<Genre>>?>(null) }
    LaunchedEffect(true) {
        genresResponse = getGenres()
    }

    Column {
        Text("Потоки", fontWeight = FontWeight.Bold, fontSize = 45.sp)
        TabRow(selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }
        when (selectedTab) {
            0 -> {
            }
            1 -> {
                val genres = genresResponse?.result
                if (genres == null) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 200.dp),
                        contentPadding = PaddingValues(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(genres) {
                            GenreCard(it)
                        }
                    }
                }
            }
            2 -> {
            }
            3 -> {
            }
            4 -> {
            }
        }
    }
}