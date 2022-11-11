package pages.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.models.MetaTagForest
import api.models.Response
import layouts.Flow
import navigation.Location
import navigation.MetaTagLocation

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TagView(metaTagsTreeResponse: Response<MetaTagForest>?, onLocationChange: (Location<*>) -> Unit = {}) {
    val metaTags = metaTagsTreeResponse?.result?.trees
    if (metaTags == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        val stateVertical = rememberScrollState(0)

        Box(Modifier.fillMaxSize()) {
            Column(Modifier.padding(10.dp).verticalScroll(stateVertical), Arrangement.spacedBy(20.dp)) {
                metaTags.forEach { tree ->
                    val hasNotEmptyBushes = tree.leaves!!.any { it.leaves?.isNotEmpty() == true }
                    Column(Modifier.padding(10.dp)) {
                        Text(tree.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Flow(horizontalSpacing = 15.dp, verticalSpacing = 10.dp) {
                            tree.leaves.forEach { bush ->
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Text(bush.title, Modifier.onClick { onLocationChange(MetaTagLocation(bush.title)) },
                                        fontWeight = if (hasNotEmptyBushes || bush.leaves?.isNotEmpty() == true)
                                            FontWeight.Bold else
                                            FontWeight.Normal)
                                    bush.leaves?.forEach { leave ->
                                        Text(leave.title, Modifier.onClick { onLocationChange(MetaTagLocation(leave.title)) })
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
}