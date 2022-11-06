package layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun Flow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 0.dp,
    verticalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(content, modifier) { measurables, constraints ->
        val rows = mutableListOf<List<Placeable>>()
        val crossAxisPositions = mutableListOf<Int>()

        var width = 0
        var height = 0

        val currentRow = mutableListOf<Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0

        val itemConstraints = Constraints(maxWidth = constraints.maxWidth)

        for (measurable in measurables) {
            val placeable = measurable.measure(itemConstraints)
            if (currentRow.isNotEmpty()
                && currentRowWidth + horizontalSpacing.roundToPx() + placeable.width > constraints.maxWidth) {
                if (rows.isNotEmpty()) {
                    height += verticalSpacing.roundToPx()
                }
                rows.add(currentRow.toList())
                crossAxisPositions.add(height)

                height += currentRowHeight
                width = max(width, currentRowWidth)

                currentRow.clear()
                currentRowWidth = 0
                currentRowHeight = 0
            }
            if (currentRow.isNotEmpty()) {
                currentRowWidth += horizontalSpacing.roundToPx()
            }
            currentRow.add(placeable)
            currentRowWidth += placeable.width
            currentRowHeight = max(currentRowHeight, placeable.height)
        }

        if (currentRow.isNotEmpty()) {
            if (rows.isNotEmpty()) {
                height += verticalSpacing.roundToPx()
            }
            rows.add(currentRow.toList())
            crossAxisPositions.add(height)

            height += currentRowHeight
            width = max(width, currentRowWidth)

            currentRow.clear()
        }

        layout(max(width, constraints.minWidth), max(height, constraints.minHeight)) {
            rows.forEachIndexed { i, placeables ->
                var offset = 0
                placeables.forEach { placeable ->
                    placeable.place(
                        x = offset,
                        y = crossAxisPositions[i]
                    )
                    offset += placeable.width + horizontalSpacing.roundToPx()
                }
            }
        }
    }
}
