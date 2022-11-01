import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import api.findSuggestions
import api.models.Suggestions
import kotlinx.coroutines.launch
import pages.SearchPage

private enum class Pages {
    HOME,
    SEARCH
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App() {

    val scaffoldState = rememberScaffoldState()
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
        Scaffold(
            drawerContent = {
                Divider()
            },
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
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
            }
        ) {
            when (page) {
                Pages.HOME -> {}
                Pages.SEARCH -> SearchPage(searchText)
            }
        }
    }
}