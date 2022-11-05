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

    var location by remember { mutableStateOf<Location<*>>(HomeLocation) }
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
                NavigationDrawerItem({ Text("Главная") }, location is HomeLocation, { location = HomeLocation }, icon = { Icon(Icons.Filled.Home, null) })
                NavigationDrawerItem({ Text("Подкасты и книги") }, location is PodcastsLocation, { location = PodcastsLocation }, icon = { Icon(Icons.Filled.Podcasts, null) })
                NavigationDrawerItem({ Text("Радио") }, location is RadiosLocation, { location = RadiosLocation }, icon = { Icon(Icons.Filled.Radio, null) })
                NavigationDrawerItem({ Text("Коллекция") }, location is CollectionLocation, { location = CollectionLocation }, icon = { Icon(Icons.Filled.Collections, null) })
                NavigationDrawerItem({ Text("Настройки") }, location is SettingsLocation, { location = SettingsLocation }, icon = { Icon(Icons.Filled.Settings, null) })
            }
        ) {
            Column {
                TopAppBar() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TextField(value = searchText, onValueChange = { searchText = it },
                            modifier = Modifier.fillMaxWidth(0.5f).onKeyEvent {
                                if (it.key == Key.Enter && searchText.isNotBlank()) {
                                    location = SearchLocation(searchText)
                                    searchText = ""
                                }
                                false
                            },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                            placeholder = { Text("Поиск") },
                            singleLine = true
                        )
                        DropdownMenu(expanded = showSuggestions, onDismissRequest = { showSuggestions = false }) {
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
                }
                when (location) {
                    is CollectionLocation -> {}
                    is ArtistLocation -> ArtistPage(location.data as Long) { location = it }
                    is AlbumLocation -> AlbumPage(location.data as Long) { location = it }
                    is HomeLocation -> HomePage() { location = it }
                    is PlaylistLocation -> PlaylistPage(location.data as PlaylistId) { location = it }
                    is PodcastLocation -> PodcastPage(location.data as Long) { location = it }
                    is PodcastsLocation -> PodcastsPage() { location = it }
                    is RadiosLocation -> RadiosPage() { location = it }
                    is SearchLocation -> SearchPage(location.data as String) { location = it }
                    is SettingsLocation -> {}
                }
                Player() { location = it }
            }
        }
    }
}