package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import api.getChart
import api.models.Chart
import api.models.ChartScope
import api.models.Response
import components.PlaylistCard
import components.TrackItem
import navigation.Location

@Composable
@Preview
fun HomePage(onLocationChange: (Location<*>) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Новые релизы", "Чарт", "Настроения и жанры")

    var chartResponse by remember { mutableStateOf<Response<Chart>?>(null) }
    LaunchedEffect(ChartScope.RUSSIA) {
        chartResponse = getChart(ChartScope.RUSSIA)
    }

    Column {
        Text("Главное", fontWeight = FontWeight.Bold, fontSize = 45.sp)
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
            }
            2 -> {
                val chartInfo = chartResponse?.result
                if (chartInfo == null) {
                    Column(Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                } else {
                    val stateVertical = rememberScrollState(0)

                    Box(Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                            Text(chartInfo.chart.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text(chartInfo.title)
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                chartInfo.chart.tracks?.let {
                                    it.forEach {
                                        TrackItem(it.track) { onLocationChange(it) }
                                    }
                                }
                            }
                            chartInfo.chart.similarPlaylists?.let {
                                Text("Плейлисты с другими чартами", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Row {
                                    it.forEach {
                                        PlaylistCard(it) { onLocationChange(it) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            3 -> {
            }
        }
    }
}