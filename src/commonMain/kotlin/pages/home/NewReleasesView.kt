package pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.Feed
import api.models.Response
import components.AlbumCard
import navigation.AlbumInfo
import navigation.Info
import navigation.Location

@Composable
internal fun NewReleasesView(feedResponse: Response<Feed>?,
                             onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    val newAlbums = feedResponse?.result?.days?.get(0)?.events?.first { event -> event is Feed.NewAlbumsOfFavoriteGenre }?.let {
        (it as Feed.NewAlbumsOfFavoriteGenre).albums
    }

    if (feedResponse == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (newAlbums != null) {
        Column {
            Text("Новые треки, альбомы и сборники", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(newAlbums) {
                    AlbumCard(it, onClick = { onInfoRequest(AlbumInfo(it.id)) }, onLocationChange = onLocationChange)
                }
            }
        }
    }
}