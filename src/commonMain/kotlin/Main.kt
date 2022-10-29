// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pages.SearchPage

@Composable
@Preview
fun App() {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }

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
                    TextField(value = searchText, onValueChange = { searchText = it },
                        modifier = Modifier.fillMaxWidth(0.5f),
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null)},
                        placeholder = { Text("Поиск") },
                        singleLine = true)
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
