// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    var showWindow by remember { mutableStateOf(true) }
    val icon = painterResource("icon_main.png")

    Tray(
        icon = icon,
        menu = {
            Item(if (showWindow) "Свернуть в трей" else "Развернуть", onClick = { showWindow = !showWindow })
            Item("Выйти", onClick = ::exitApplication)
        }
    )

    if (showWindow) {
        Window(onCloseRequest = ::exitApplication, icon = icon, title = "Яндекс Музыка") {
            App()
        }
    }
}
