package pages.artist

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import api.models.Artist
import api.models.ArtistInfo
import components.AsyncImage
import components.loadImageBitmap
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.*
import layouts.Flow
import util.openInBrowser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoView(artistInfo: ArtistInfo) {
    val stateVertical = rememberScrollState(0)
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(10.dp).verticalScroll(stateVertical)) {
            if (artistInfo.allCovers.size > 1) {
                Flow(horizontalSpacing = 5.dp) {
                    artistInfo.allCovers.forEach {
                        AsyncImage(
                            load = { loadImageBitmap("https://" + it.uri!!.replace("%%", "1000x1000")) },
                            painterFor = { remember { BitmapPainter(it) } },
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.width(290.dp).height(185.dp)
                        )
                    }
                }
            }
            artistInfo.stats?.let {
                Text("Слушателей за предыдущий месяц: ${it.lastMonthListeners}")
            }
            Row(Modifier.fillMaxWidth()) {
                artistInfo.artist.description?.let {
                    Card(Modifier.fillMaxWidth(0.5f)) {
                        Column {
                            Text("Об исполнителе", fontWeight = FontWeight.Bold)
                            Text(it.text)
                        }
                    }
                }
                artistInfo.artist.links?.let {
                    Card(Modifier.fillMaxWidth(0.5f)) {
                        Column {
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
}