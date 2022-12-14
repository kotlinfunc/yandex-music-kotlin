package navigation

import api.models.PlaylistId

sealed class Location<T>(val data: T)

class ArtistLocation(id: Long): Location<Long>(id)

class AlbumLocation(id: Long): Location<Long>(id)

object CollectionLocation: Location<Unit>(Unit)

object HomeLocation: Location<Unit>(Unit)

class MetaTagLocation(tag: String): Location<String>(tag)

class PlaylistLocation(id: PlaylistId): Location<PlaylistId>(id)

class PodcastLocation(id: Long): Location<Long>(id)

object PodcastsLocation: Location<Unit>(Unit)

object RadiosLocation: Location<Unit>(Unit)

class SearchLocation(searchText: String): Location<String>(searchText)

object SettingsLocation: Location<Unit>(Unit)