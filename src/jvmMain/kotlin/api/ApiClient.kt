package api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

private val client = HttpClient() {
    install(ContentNegotiation) {
        json()
    }
    install(Logging)
}