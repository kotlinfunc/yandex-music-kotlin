package pages.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import api.getAlbumWithTracks
import api.getArtistDirectAlbums
import api.getMetaTagAlbums
import api.models.Album
import api.models.ArtistAlbums
import api.models.MetaTag
import api.models.Response
import navigation.Info
import navigation.Location

@Composable
fun AlbumPage(id: Long, onInfoRequest: (Info<*>) -> Unit = {}, onLocationChange: (Location<*>) -> Unit = {}) {
    var loading by remember { mutableStateOf(true) }
    var albumResponse by remember { mutableStateOf<Response<Album>?>(null) }
    var genreAlbumsResponse by remember { mutableStateOf<Response<MetaTag>?>(null) }
    var otherAlbumsResponse by remember { mutableStateOf<Response<ArtistAlbums>?>(null) }

    LaunchedEffect(id) {
        loading = true
        val albumWithTracks = getAlbumWithTracks(id)
        otherAlbumsResponse = albumWithTracks.result?.artists?.get(0)?.let{
            getArtistDirectAlbums(it.id)
        }
        genreAlbumsResponse = albumWithTracks.result?.metaTagId?.let {
            getMetaTagAlbums(it, pageSize = 10)
        }
        albumResponse = albumWithTracks
        loading = false
    }

    if (loading) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (albumResponse?.error != null) {
        Text("Ошибка: ${albumResponse?.error?.message}")
    } else {
        albumResponse?.result?.let { album ->
            Column {
                InfoPanel(album, onLocationChange)
                MainView(album, genreAlbumsResponse, otherAlbumsResponse, onInfoRequest, onLocationChange)
            }
        }
    }
}