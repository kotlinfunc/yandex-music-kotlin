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
import api.models.Suggestions
import components.AsyncImage
import components.Player
import components.loadSvgPainter
import kotlinx.coroutines.launch
import pages.SearchPage
import java.io.File

private enum class Pages {
    COLLECTION,
    HOME,
    PODCASTS,
    RADIO,
    SEARCH,
    SETTINGS,

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val density = LocalDensity.current
    val drawerState = rememberDrawerState(DrawerValue.Open)

    val scope = rememberCoroutineScope()

    var page by remember { mutableStateOf(Pages.HOME) }
    var searchText by remember { mutableStateOf("") }
    var searchSuggestions by remember { mutableStateOf<Suggestions?>(null) }
    var showSuggestions by remember { mutableStateOf(false) }

    LaunchedEffect(searchText) {
        if (searchText.isNotBlank()) {
            scope.launch {
                searchSuggestions = findSuggestions(searchText).result
                showSuggestions = true
            }
        }
    }

    MaterialTheme {
        DismissibleNavigationDrawer(
            {
                AsyncImage(
                    load = { loadSvgPainter(this.javaClass.getResourceAsStream("/logo_semantic_horizontal_black.svg")!!, density) },
                    painterFor = { it },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(100.dp).fillMaxWidth().padding(12.dp)
                )
                Divider()
                NavigationDrawerItem({ Text("Главная") }, page == Pages.HOME, { page = Pages.HOME }, icon = { Icon(Icons.Filled.Home, null) })
                NavigationDrawerItem({ Text("Подкасты и книги") }, page == Pages.PODCASTS, { page = Pages.PODCASTS }, icon = { Icon(Icons.Filled.Podcasts, null) })
                NavigationDrawerItem({ Text("Радио") }, page == Pages.RADIO, { page = Pages.RADIO }, icon = { Icon(Icons.Filled.Radio, null) })
                NavigationDrawerItem({ Text("Коллекция") }, page == Pages.COLLECTION, { page = Pages.COLLECTION }, icon = { Icon(Icons.Filled.Collections, null) })
                NavigationDrawerItem({ Text("Настройки") }, page == Pages.SETTINGS, { page = Pages.SETTINGS }, icon = { Icon(Icons.Filled.Settings, null) })
            },
            Modifier.padding(horizontal = 12.dp),
            drawerState
        ) {
            Column {
                TopAppBar() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        TextField(value = searchText, onValueChange = { searchText = it },
                            modifier = Modifier.fillMaxWidth(0.5f).onKeyEvent {
                                if (it.key == Key.Enter && searchText.isNotBlank()) {
                                    page = Pages.SEARCH
                                }
                                false
                            },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null)},
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
                when (page) {
                    Pages.SEARCH -> SearchPage(searchText)
                    else -> {}
                }
                Player()
            }
        }
    }
}