// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import api.findSuggestions
import api.models.Suggestions
import kotlinx.coroutines.launch
import pages.SearchPage

@Composable
@Preview
fun App() {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                    Button(onClick = {}) {
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
                            modifier = Modifier.fillMaxWidth(0.5f),
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null)},
                            placeholder = { Text("Поиск") },
                            singleLine = true)
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
            SearchPage()
        }
    }
}

fun main() = application {
    val icon = painterResource("icon_main.png")

    Tray(
        icon = icon,
        menu = {
            Item("Выйти", onClick = ::exitApplication)
        }
    )

    Window(onCloseRequest = ::exitApplication, icon = icon, title = "Яндекс Музыка") {
        App()
    }
}
