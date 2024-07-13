package app.myzel394.numberhub.feature.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.myzel394.numberhub.data.common.format
import app.myzel394.numberhub.feature.converter.UnitConverterUIState
import java.math.BigDecimal

@Composable
internal fun ValueOneSummary(
    modifier: Modifier = Modifier,
    uiState: UnitConverterUIState.Default,
) {
    val unitFromLabel = LocalContext.current.getString(uiState.unitFrom.displayName)
    val unitToLabel = LocalContext.current.getString(uiState.unitTo.displayName)
    val value = uiState.unitFrom.convert(uiState.unitTo, BigDecimal(1))
        .format(uiState.scale, uiState.outputFormat)

    val fontStyle = MaterialTheme.typography.headlineSmall

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            1.toString(),
            style = fontStyle,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            " ",
            style = fontStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            unitFromLabel,
            style = fontStyle,
            color = MaterialTheme.colorScheme.tertiary,
        )

        Text(
            " = ",
            style = fontStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            value,
            style = fontStyle,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            " ",
            style = fontStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            unitToLabel,
            style = fontStyle,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}