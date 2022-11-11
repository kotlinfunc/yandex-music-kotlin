package pages.home

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.Chart
import api.models.PlaylistId
import api.models.Response
import components.PlaylistCard
import components.TrackItem
import layouts.Flow
import navigation.Info
import navigation.Location
import navigation.PlaylistInfo
import navigation.TrackInfo

@Composable
internal fun ChartView(chartResponse: Response<Chart>?,
                       onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    chartResponse?.result?.let { chartInfo ->
        val stateVertical = rememberScrollState(0)
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
                Text(chartInfo.chart.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(chartInfo.title)
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    chartInfo.chart.tracks?.let {
                        it.forEach {
                            TrackItem(it.track!!, onClick = { onInfoRequest(TrackInfo(it.id)) }, onLocationChange = onLocationChange)
                        }
                    }
                }
                chartInfo.chart.similarPlaylists?.let {
                    Text("Плейлисты с другими чартами", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                        it.forEach {
                            PlaylistCard(it, onClick = { onInfoRequest(PlaylistInfo(PlaylistId(it.uid, it.kind))) }, onLocationChange = onLocationChange)
                        }
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical)
            )
        }
    } ?: Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}