package layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.lang.Integer.max

@Composable
fun TruncatedRow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit) {
    Layout(content, modifier) {measurables, constraints ->
        val items = mutableListOf<Placeable>()
        val positions = mutableListOf<Int>()

        var width = 0
        var height = 0

        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        val itemConstraints = Constraints(maxWidth = constraints.maxWidth)
        for (measurable in measurables) {
            val placeable = measurable.measure(itemConstraints)

            if (items.isNotEmpty() && width + placeable.width > constraints.maxWidth) {
                break
            }

            items.add(placeable)
            positions.add(width)
            width += placeable.width + horizontalSpacingPx
            height = max(height, placeable.height)
        }
        width -= horizontalSpacingPx

        layout(max(width, constraints.minWidth), max(height, constraints.minHeight)) {
            items.forEachIndexed { index, placeable ->
                placeable.placeRelative(positions[index],0)
            }
        }
    }
}