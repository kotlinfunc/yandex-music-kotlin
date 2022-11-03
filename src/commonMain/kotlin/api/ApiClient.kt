package api

import api.models.*
import api.resources.Albums
import api.resources.Artists
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

suspend fun findSuggestions(search: String): Response<Suggestions> {
    return client.get(api.resources.Search.Suggest(part = search)).body()
}

suspend fun search(query: String): Response<Search> {
    return client.get(api.resources.Search(query)).body()
}