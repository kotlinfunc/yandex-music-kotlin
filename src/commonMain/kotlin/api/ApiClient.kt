package api

import api.models.Response
import api.models.Suggestions
import api.resources.Search
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

suspend fun findSuggestions(search: String): Response<Suggestions> {
    return client.get(Search.Suggest(part = search)).body()
}