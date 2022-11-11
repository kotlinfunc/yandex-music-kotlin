package pages.artist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import api.models.Artist
import api.models.ArtistInfo
import components.AsyncImage
import components.loadImageBitmap
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.*
import layouts.Flow
import util.openInBrowser

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun InfoView(artistInfo: ArtistInfo) {
    var selectedImage by remember { mutableStateOf(-1) }

    val stateVertical = rememberScrollState(0)
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            if (artistInfo.allCovers.size > 1) {
                Flow(horizontalSpacing = 5.dp) {
                    artistInfo.allCovers.forEachIndexed { idx, cover ->
                        AsyncImage(
                            load = { loadImageBitmap("https://" + cover.uri!!.replace("%%", "1000x1000")) },
                            painterFor = { remember { BitmapPainter(it) } },
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.onClick { selectedImage = idx }.width(290.dp).height(185.dp)
                        )
                    }
                }
            }
            artistInfo.stats?.let {
                Text("Слушателей за предыдущий месяц: ${it.lastMonthListeners}")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                artistInfo.artist.description?.let {
                    Card(Modifier.weight(0.5f)) {
                        Column(Modifier.padding(10.dp)) {
                            Text("Об исполнителе", fontWeight = FontWeight.Bold)
                            Text(it.text)
                        }
                    }
                }
                artistInfo.artist.links?.let {
                    Card(Modifier.weight(0.5f)) {
                        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Официальные страницы", fontWeight = FontWeight.Bold)
                            it.forEach {
                                OutlinedButton({ openInBrowser(it.href) }) {
                                    Icon(
                                        if (it.type == Artist.Link.Type.SOCIAL)
                                            when (it.socialNetwork!!) {
                                                Artist.Link.SocialNetwork.DISCORD -> FontAwesomeIcons.Brands.Discord
                                                Artist.Link.SocialNetwork.FACEBOOK -> FontAwesomeIcons.Brands.Facebook
                                                Artist.Link.SocialNetwork.TELEGRAM -> FontAwesomeIcons.Brands.Telegram
                                                Artist.Link.SocialNetwork.TIKTOK -> FontAwesomeIcons.Brands.Tiktok
                                                Artist.Link.SocialNetwork.TWITTER -> FontAwesomeIcons.Brands.Twitter
                                                Artist.Link.SocialNetwork.VK -> FontAwesomeIcons.Brands.Vk
                                                Artist.Link.SocialNetwork.YOUTUBE -> FontAwesomeIcons.Brands.Youtube
                                                Artist.Link.SocialNetwork.ZEN -> FontAwesomeIcons.Brands.Yandex
                                            }
                                        else Icons.Filled.Language,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize)
                                    )
                                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                    Text(it.title)
                                }
                            }
                        }
                    }
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }

    if (selectedImage >= 0) {
        Dialog({ selectedImage = -1 }, title = "${artistInfo.artist.name}: фотогалерея") {
            Row(Modifier.fillMaxHeight(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                IconButton({ selectedImage-- }, enabled = selectedImage > 0) {
                    Icon(
                        Icons.Filled.NavigateBefore,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                AsyncImage(
                    load = { loadImageBitmap("https://" + artistInfo.allCovers[selectedImage].uri!!.replace("%%", "1000x1000")) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.weight(1f)
                )
                IconButton({ selectedImage++ }, enabled = selectedImage < artistInfo.allCovers.size - 1) {
                    Icon(
                        Icons.Filled.NavigateNext,
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        }
    }
}