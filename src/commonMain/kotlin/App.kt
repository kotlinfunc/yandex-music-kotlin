import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import api.findSuggestions
import api.models.PlaylistId
import api.models.Suggestions
import components.AsyncImage
import components.Player
import components.loadSvgPainter
import navigation.*
import pages.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val density = LocalDensity.current

    val previousLocations = mutableStateListOf<Location<*>>()
    var location by remember { mutableStateOf<Location<*>>(HomeLocation) }
    fun changeLocation(newLocation: Location<*>) {
        previousLocations.add(location)
        if (previousLocations.size > 100) {
            previousLocations.removeFirst()
        }
        location = newLocation
    }

    var searchText by remember { mutableStateOf("") }
    var searchSuggestions by remember { mutableStateOf<Suggestions?>(null) }
    var showSuggestions by remember { mutableStateOf(false) }

    LaunchedEffect(searchText) {
        if (searchText.isNotBlank()) {
            searchSuggestions = findSuggestions(searchText).result
            showSuggestions = true
        }
    }

    MaterialTheme {
        PermanentNavigationDrawer(
            {
                AsyncImage(
                    load = { loadSvgPainter(this.javaClass.getResourceAsStream("/logo_semantic_horizontal_black.svg")!!, density) },
                    painterFor = { it },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(100.dp).fillMaxWidth().padding(12.dp)
                )
                Divider()
                NavigationDrawerItem({ Text("Главная") }, location is HomeLocation,
                    {
                        changeLocation(HomeLocation)
                    }, icon = { Icon(Icons.Filled.Home, null) })
                NavigationDrawerItem({ Text("Подкасты и книги") }, location is PodcastsLocation,
                    {
                        changeLocation(PodcastsLocation)
                    },
                    icon = { Icon(Icons.Filled.Podcasts, null) })
                NavigationDrawerItem({ Text("Радио") }, location is RadiosLocation,
                    {
                        changeLocation(RadiosLocation)
                    }, icon = { Icon(Icons.Filled.Radio, null) })
                Spacer(Modifier.weight(1f))
                NavigationDrawerItem({ Text("Коллекция") }, location is CollectionLocation,
                    {
                        changeLocation(CollectionLocation)
                    }, icon = { Icon(Icons.Filled.Collections, null) })
                NavigationDrawerItem({ Text("Настройки") }, location is SettingsLocation,
                    { changeLocation(SettingsLocation) }, icon = { Icon(Icons.Filled.Settings, null) })
            }
        ) {
            Column {
                TopAppBar() {
                    IconButton({ location = previousLocations.removeLast() }, enabled = previousLocations.isNotEmpty()) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    Row(Modifier.weight(0.5f).wrapContentSize(Alignment.TopStart)) {
                        TextField(searchText, { searchText = it },
                            Modifier.fillMaxWidth().onKeyEvent {
                                if (it.key == Key.Enter && searchText.isNotBlank()) {
                                    changeLocation(SearchLocation(searchText))
                                    searchText = ""
                                }
                                false
                            },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                            trailingIcon = {
                                IconButton({ searchText = "" }, enabled = searchText.isNotEmpty()) {
                                    Icon(
                                        Icons.Filled.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize)
                                    )
                                } },
                            placeholder = { Text("Поиск") },
                            singleLine = true
                        )
                        DropdownMenu(showSuggestions, { showSuggestions = false }) {
                            searchSuggestions!!.suggestions.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
                                        showSuggestions = false
                                        searchText = option
                                    }
                                ) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.weight(0.5f))
                    IconButton({ location = previousLocations.removeLast() }, enabled = previousLocations.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Login,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
                Column(Modifier.weight(1f)) {
                    when (location) {
                        is CollectionLocation -> {}
                        is ArtistLocation -> ArtistPage(location.data as Long) { changeLocation(it) }
                        is AlbumLocation -> AlbumPage(location.data as Long) { changeLocation(it) }
                        is HomeLocation -> HomePage { changeLocation(it) }
                        is PlaylistLocation -> PlaylistPage(location.data as PlaylistId) { changeLocation(it) }
                        is PodcastLocation -> PodcastPage(location.data as Long) { changeLocation(it) }
                        is PodcastsLocation -> PodcastsPage() { changeLocation(it) }
                        is RadiosLocation -> RadiosPage() { changeLocation(it) }
                        is SearchLocation -> SearchPage(location.data as String) { changeLocation(it) }
                        is SettingsLocation -> {}
                    }
                }
                BottomAppBar {
                    Player() { changeLocation(it) }
                }
            }
        }
    }
}