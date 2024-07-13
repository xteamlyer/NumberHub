package app.myzel394.numberhub.feature.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.myzel394.numberhub.data.converter.ConverterResult
import app.myzel394.numberhub.data.model.converter.unit.BasicUnit

@Composable
internal fun BaseCalculationSummary(
    modifier: Modifier = Modifier,
    basis: BasicUnit.NumberBase,
    result: ConverterResult.NumberBase,
) {
    val fontStyle = MaterialTheme.typography.headlineSmall

    Row(
        modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (index in 0..<result.value.length) {
            val character = result.value[index]
            val digit = character.digitToInt(16);
            val base = basis.factor.toInt()

            Text(
                text = "$digit",
                style = fontStyle,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = " · $base",
                style = fontStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "${result.value.length - index - 1}",
                modifier = Modifier.offset(y = -(MaterialTheme.typography.bodySmall.fontSize.div(2)).value.dp),
                style = fontStyle.copy(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                ),
                color = MaterialTheme.colorScheme.tertiary,
            )

            if (index < result.value.length - 1) {
                Text(
                    text = " + ",
                    style = fontStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
