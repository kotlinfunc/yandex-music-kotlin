package api.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/playlists/list")
class Playlists(val playlistIds: List<String>)