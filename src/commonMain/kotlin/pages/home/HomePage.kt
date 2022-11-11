package pages.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import api.getFeed
import api.getLandingChart
import api.getLandingMetaTags
import api.models.*
import navigation.Info
import navigation.Location

@Composable
@Preview
fun HomePage(onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Всё", "Новые релизы", "Чарт", "Настроения и жанры")

    var feedResponse by remember { mutableStateOf<Response<Feed>?>(null) }
    LaunchedEffect(true) {
        feedResponse = getFeed()
    }

    var chartResponse by remember { mutableStateOf<Response<Chart>?>(null) }
    LaunchedEffect(ChartScope.RUSSIA) {
        chartResponse = getLandingChart(ChartScope.RUSSIA)
    }

    var metaTagsTreeResponse by remember { mutableStateOf<Response<MetaTagForest>?>(null) }
    LaunchedEffect(true) {
        metaTagsTreeResponse = getLandingMetaTags()
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
            0 -> Overview(feedResponse, chartResponse, onInfoRequest, onLocationChange) { selectedTab = it }
            1 -> NewReleasesView(feedResponse, onInfoRequest, onLocationChange)
            2 -> ChartView(chartResponse, onInfoRequest, onLocationChange)
            3 -> TagView(metaTagsTreeResponse, onLocationChange)
        }
    }
}