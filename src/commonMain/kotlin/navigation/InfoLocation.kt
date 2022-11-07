package navigation

import api.models.PlaylistId

sealed class Info<T>(val data: T)

class ArtistInfo(id: Long): Info<Long>(id)

class AlbumInfo(id: Long): Info<Long>(id)

class EpisodeInfo(id: Long): Info<Long>(id)

class PlaylistInfo(id: PlaylistId): Info<PlaylistId>(id)

class PodcastInfo(id: Long): Info<Long>(id)

class TrackInfo(id: Long): Info<Long>(id)
