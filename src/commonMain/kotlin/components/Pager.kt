package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.lang.Integer.max
import java.lang.Integer.min

private fun calculatePageRange(currentPage: Int, totalPages: Int): IntRange {
    return max(1, currentPage - 2).rangeTo(min(max(currentPage + 2, 5), totalPages))
}

@Composable
@Preview
fun Pager(page: Int = 1, totalPages: Int = 1, onPageChange: (Int) -> Unit = {}) {
    Row(Modifier, Arrangement.spacedBy(5.dp)) {
        if (page > 5) {
            TextButton({ onPageChange(1) }) {
                Text("В начало")
            }
            TextButton({ onPageChange(page - 5) }) {
                Text("Предыдущие 5")
            }
        }
        calculatePageRange(page, totalPages).forEach {
            if (page == it) {
                Button({ onPageChange(it) }) {
                    Text("$it")
                }
            } else {
                OutlinedButton({ onPageChange(it) }) {
                    Text("$it")
                }
            }
        }
        if (totalPages - page > 5) {
            TextButton({ onPageChange(page + 5) }) {
                Text("Следующие 5")
            }
        }
    }
}