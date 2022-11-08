package api

import api.models.*
import api.models.Feed
import api.models.Search
import api.resources.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val client = HttpClient {
    defaultRequest {
        url("https://api.music.yandex.net/")
    }
    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
}

suspend fun getAlbum(id: Long): Response<Album> {
    return client.get(Albums.Get(id = id)).body()
}

suspend fun getAlbumWithTracks(id: Long): Response<Album> {
    return client.get(Albums.Get.WithTracks(Albums.Get(id = id))).body()
}

suspend fun getArtist(id: Long): Response<ArtistInfo> {
    return client.get(Artists.Get(id = id)).body()
}

suspend fun getArtistBriefInfo(id: Long): Response<ArtistInfo> {
    return client.get(Artists.Get.BriefInfo(Artists.Get(id = id))).body()
}

suspend fun getArtistDirectAlbums(id: Long): Response<ArtistAlbums> {
    return client.get(Artists.Get.DirectAlbums(Artists.Get(id = id))).body()
}

suspend fun getEpisode(id: Long): Response<List<Episode>> {
    return client.get(Tracks.Get(id = id)).body()
}

suspend fun getEpisodeSupplement(id: Long): Response<EpisodeSupplement> {
    return client.get(Tracks.Get.Supplement(Tracks.Get(id = id))).body()
}

suspend fun getFeed(): Response<Feed> {
    return client.get(api.resources.Feed()).body()
}

suspend fun getGenres(): Response<List<Genre>> {
    return client.get(Genres()).body()
}

suspend fun getLandingChart(chartType: ChartScope): Response<Chart> {
    return client.get(Landing.Chart(chartType = chartType)).body()
}

suspend fun getLandingMetaTags(): Response<MetaTagForest> {
    return client.get(Landing.MetaTags()).body()
}

suspend fun getPlaylists(playlistIds: List<PlaylistId>): Response<List<Playlist>> {
    return client.post(Playlists(playlistIds.map { "${it.uid}:${it.kind}" })).body()
}

suspend fun getMetaTag(tag: String): Response<MetaTag> {
    return client.get(MetaTags(tag)).body()
}

suspend fun getMetaTagAlbums(tag: String, page: Int = 0, pageSize: Int = 20, sortBy: String? = null): Response<MetaTag> {
    return client.get(MetaTags.Albums(MetaTags(tag), page * pageSize, pageSize, sortBy)).body()
}

suspend fun getMetaTagArtists(tag: String, page: Int = 0, pageSize: Int = 20, sortBy: String? = null): Response<MetaTag> {
    return client.get(MetaTags.Artists(MetaTags(tag), page * pageSize, pageSize, sortBy)).body()
}

suspend fun getMetaTagPlaylists(tag: String, page: Int = 0, pageSize: Int = 20, sortBy: String? = null): Response<MetaTag> {
    return client.get(MetaTags.Playlists(MetaTags(tag), page * pageSize, pageSize, sortBy)).body()
}

suspend fun getPodcastWithEpisodes(id: Long): Response<Podcast> {
    return client.get(Albums.Get.WithTracks(Albums.Get(id = id))).body()
}

suspend fun getTrack(id: Long): Response<List<Track>> {
    return client.get(Tracks.Get(id = id)).body()
}

suspend fun getTrackDownloadInfo(id: Long): Response<DownloadInfo> {
    return client.get(Tracks.Get.DownloadInfo(Tracks.Get(id = id))).body()
}

suspend fun getTrackSimilar(id: Long): Response<SimilarTracks> {
    return client.get(Tracks.Get.Similar(Tracks.Get(id = id))).body()
}

suspend fun getTrackSupplement(id: Long): Response<TrackSupplement> {
    return client.get(Tracks.Get.Supplement(Tracks.Get(id = id))).body()
}

suspend fun getUserPlaylist(playlistId: PlaylistId): Response<Playlist> {
    return client.get(Users.Get.Playlists.ByKind(Users.Get.Playlists(Users.Get(id = playlistId.uid)), playlistId.kind)).body()
}

suspend fun getUserPlaylists(uid: Long): Response<List<Playlist>> {
    return client.get(Users.Get.Playlists.List(Users.Get.Playlists(Users.Get(id = uid)))).body()
}

suspend fun getVideo(id: Long): Response<Video> {
    return client.get(Clips.Get(id = id)).body()
}

suspend fun findSuggestions(search: String): Response<Suggestions> {
    return client.get(api.resources.Search.Suggest(part = search)).body()
}

suspend fun search(query: String): Response<Search> {
    return client.get(api.resources.Search(query)).body()
}