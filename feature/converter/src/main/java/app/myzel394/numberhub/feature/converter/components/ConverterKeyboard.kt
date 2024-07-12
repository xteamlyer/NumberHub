/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package app.myzel394.numberhub.feature.converter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.myzel394.numberhub.core.base.R
import app.myzel394.numberhub.core.base.Token
import app.myzel394.numberhub.core.ui.LocalWindowSize
import app.myzel394.numberhub.core.ui.WindowHeightSizeClass
import app.myzel394.numberhub.core.ui.common.ColumnWithConstraints
import app.myzel394.numberhub.core.ui.common.KeyboardButtonFilled
import app.myzel394.numberhub.core.ui.common.KeyboardButtonLight
import app.myzel394.numberhub.core.ui.common.KeyboardButtonTertiary
import app.myzel394.numberhub.core.ui.common.KeyboardButtonToken
import app.myzel394.numberhub.core.ui.common.KeypadFlow
import app.myzel394.numberhub.core.ui.common.icons.IconPack
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Backspace
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Brackets
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Clear
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Comma
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Divide
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Dot
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key0
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key1
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key2
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key3
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key4
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key5
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key6
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key7
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key8
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Key9
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyA
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyB
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyC
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyD
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyE
import app.myzel394.numberhub.core.ui.common.icons.iconpack.KeyF
import app.myzel394.numberhub.core.ui.common.icons.iconpack.LeftBracket
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Minus
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Multiply
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Plus
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Power
import app.myzel394.numberhub.core.ui.common.icons.iconpack.RightBracket
import app.myzel394.numberhub.core.ui.common.icons.iconpack.Root
import app.myzel394.numberhub.data.model.converter.unit.BasicUnit
import kotlin.math.ceil

@Composable
internal fun DefaultKeyboard(
    modifier: Modifier,
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    fractional: String,
    middleZero: Boolean,
    acButton: Boolean,
    addBracket: () -> Unit,
) {
    val fractionalIcon = remember(fractional) { if (fractional == Token.PERIOD) IconPack.Dot else IconPack.Comma }
    val fractionalIconDescription = remember(fractional) { if (fractional == Token.PERIOD) R.string.keyboard_dot else R.string.comma }
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonToken.CONTENT_HEIGHT_SHORT else KeyboardButtonToken.CONTENT_HEIGHT_TALL

    KeypadFlow(
        modifier = modifier,
        rows = 5,
        columns = 4,
    ) { width, height ->
        val bModifier = Modifier
            .fillMaxWidth(width)
            .fillMaxHeight(height)

        if (acButton) {
            KeyboardButtonTertiary(bModifier, IconPack.Clear, stringResource(R.string.delete_label), contentHeight) { clearInput() }
            KeyboardButtonFilled(bModifier, IconPack.Brackets, stringResource(R.string.keyboard_brackets), contentHeight) { addBracket() }
        } else {
            KeyboardButtonFilled(bModifier, IconPack.LeftBracket, stringResource(R.string.keyboard_left_bracket), contentHeight) { addDigit(Token.Operator.leftBracket) }
            KeyboardButtonFilled(bModifier, IconPack.RightBracket, stringResource(R.string.keyboard_right_bracket), contentHeight) { addDigit(Token.Operator.rightBracket) }
        }
        KeyboardButtonFilled(bModifier, IconPack.Power, stringResource(R.string.keyboard_power), contentHeight) { addDigit(Token.Operator.power) }
        KeyboardButtonFilled(bModifier, IconPack.Root, stringResource(R.string.keyboard_root), contentHeight) { addDigit(Token.Operator.sqrt) }

        KeyboardButtonLight(bModifier, IconPack.Key7, Token.Digit._7, contentHeight) { addDigit(Token.Digit._7) }
        KeyboardButtonLight(bModifier, IconPack.Key8, Token.Digit._8, contentHeight) { addDigit(Token.Digit._8) }
        KeyboardButtonLight(bModifier, IconPack.Key9, Token.Digit._9, contentHeight) { addDigit(Token.Digit._9) }
        KeyboardButtonFilled(bModifier, IconPack.Divide, stringResource(R.string.keyboard_divide), contentHeight) { addDigit(Token.Operator.divide) }

        KeyboardButtonLight(bModifier, IconPack.Key4, Token.Digit._4, contentHeight) { addDigit(Token.Digit._4) }
        KeyboardButtonLight(bModifier, IconPack.Key5, Token.Digit._5, contentHeight) { addDigit(Token.Digit._5) }
        KeyboardButtonLight(bModifier, IconPack.Key6, Token.Digit._6, contentHeight) { addDigit(Token.Digit._6) }
        KeyboardButtonFilled(bModifier, IconPack.Multiply, stringResource(R.string.keyboard_multiply), contentHeight) { addDigit(Token.Operator.multiply) }

        KeyboardButtonLight(bModifier, IconPack.Key1, Token.Digit._1, contentHeight) { addDigit(Token.Digit._1) }
        KeyboardButtonLight(bModifier, IconPack.Key2, Token.Digit._2, contentHeight) { addDigit(Token.Digit._2) }
        KeyboardButtonLight(bModifier, IconPack.Key3, Token.Digit._3, contentHeight) { addDigit(Token.Digit._3) }
        KeyboardButtonFilled(bModifier, IconPack.Minus, stringResource(R.string.keyboard_minus), contentHeight) { addDigit(Token.Operator.minus) }

        if (middleZero) {
            KeyboardButtonLight(bModifier, fractionalIcon, stringResource(fractionalIconDescription), contentHeight) { addDigit(Token.Digit.dot) }
            KeyboardButtonLight(bModifier, IconPack.Key0, Token.Digit._0, contentHeight) { addDigit(Token.Digit._0) }
        } else {
            KeyboardButtonLight(bModifier, IconPack.Key0, Token.Digit._0, contentHeight) { addDigit(Token.Digit._0) }
            KeyboardButtonLight(bModifier, fractionalIcon, stringResource(fractionalIconDescription), contentHeight) { addDigit(Token.Digit.dot) }
        }
        KeyboardButtonLight(bModifier, IconPack.Backspace, stringResource(R.string.delete_label), contentHeight, onLongClick = clearInput) { deleteDigit() }
        KeyboardButtonFilled(bModifier, IconPack.Plus, stringResource(R.string.keyboard_plus), contentHeight) { addDigit(Token.Operator.plus) }
    }
}

val AVAILABLE_NUMBERS = mapOf<String, ImageVector>(
    Token.Digit._0 to IconPack.Key0,
    Token.Digit._1 to IconPack.Key1,
    Token.Digit._2 to IconPack.Key2,
    Token.Digit._3 to IconPack.Key3,
    Token.Digit._4 to IconPack.Key4,
    Token.Digit._5 to IconPack.Key5,
    Token.Digit._6 to IconPack.Key6,
    Token.Digit._7 to IconPack.Key7,
    Token.Digit._8 to IconPack.Key8,
    Token.Digit._9 to IconPack.Key9,
    Token.Letter._A to IconPack.KeyA,
    Token.Letter._B to IconPack.KeyB,
    Token.Letter._C to IconPack.KeyC,
    Token.Letter._D to IconPack.KeyD,
    Token.Letter._E to IconPack.KeyE,
    Token.Letter._F to IconPack.KeyF,
)

@Composable
internal fun NumberBaseKeyboard(
    modifier: Modifier,
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    basis: BasicUnit.NumberBase,
) {
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonToken.CONTENT_HEIGHT_SHORT else KeyboardButtonToken.CONTENT_HEIGHT_TALL
    val amount = basis.factor.toInt()

    val columns: Int = when {
        amount == 5 -> 2
        amount == 3 -> 2
        amount == 7 -> 2
        amount == 10 -> 3
        amount < 10 -> if (amount.and(1) == 0) 2 else if (amount % 3 == 0) 3 else amount % 3
        else -> 3
    }
    val rows = when {
        amount == 5 -> 3
        amount == 3 -> 2
        amount == 7 -> 4
        amount == 10 -> 4
        amount < 10 -> ceil(amount.toDouble() / columns.toDouble()).toInt() + 1
        amount == 11 -> 5
        amount == 12 -> 5
        amount == 13 -> 5
        else -> 6
    }

    ColumnWithConstraints(Modifier) { constraints ->
        val horizontalSpacing = 8.dp
        val verticalSpacing = 12.dp
        val height: Float = (1f - (verticalSpacing * (rows - 1) / constraints.maxHeight)) / rows

        FlowRow(
            modifier = modifier,
            maxItemsInEachRow = columns,
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        ) {
            val bModifier = Modifier
                .fillMaxHeight(height)
                .fillMaxWidth()

            when {
                amount in arrayOf(3, 5, 7) -> {
                    for (int in createSortedArray(1..<amount, columns)) {
                        val key = AVAILABLE_NUMBERS.keys.elementAt(int)
                        val icon = AVAILABLE_NUMBERS[key]!!
                        KeyboardButtonLight(
                            bModifier.weight(1f),
                            icon,
                            key,
                            contentHeight,
                        ) { addDigit(key) }
                    }

                    KeyboardButtonLight(
                        bModifier.weight(1f),
                        IconPack.Key0,
                        Token.Digit._0,
                        contentHeight,
                    ) { addDigit(Token.Digit._0) }
                    KeyboardButtonTertiary(
                        bModifier.weight(1f),
                        IconPack.Backspace,
                        stringResource(R.string.delete_label),
                        contentHeight,
                        clearInput,
                    ) { deleteDigit() }
                }

                amount < 10 -> {
                    for (int in createSortedArray(0..<amount.coerceAtMost(10), columns)) {
                        val key = AVAILABLE_NUMBERS.keys.elementAt(int)
                        val icon = AVAILABLE_NUMBERS[key]!!
                        KeyboardButtonLight(
                            bModifier.weight(1f),
                            icon,
                            key,
                            contentHeight,
                        ) { addDigit(key) }
                    }

                    KeyboardButtonTertiary(
                        bModifier,
                        IconPack.Backspace,
                        stringResource(R.string.delete_label),
                        contentHeight,
                        clearInput,
                    ) { deleteDigit() }
                }

                amount == 10 -> {
                    for (int in createSortedArray(1..9, columns)) {
                        val key = AVAILABLE_NUMBERS.keys.elementAt(int)
                        val icon = AVAILABLE_NUMBERS[key]!!
                        KeyboardButtonLight(
                            bModifier.weight(1f),
                            icon,
                            key,
                            contentHeight,
                        ) { addDigit(key) }
                    }

                    KeyboardButtonLight(
                        bModifier.weight(1f),
                        IconPack.Key0,
                        Token.Digit._0,
                        contentHeight,
                    ) { addDigit(Token.Digit._0) }
                    KeyboardButtonTertiary(
                        bModifier.weight(2f),
                        IconPack.Backspace,
                        stringResource(R.string.delete_label),
                        contentHeight,
                        clearInput,
                    ) { deleteDigit() }
                }

                else -> {
                    val lettersAmount = (10..<amount.coerceAtMost(16)).count().toFloat()
                    val rowsAmount = ceil(lettersAmount / columns.toFloat()).toInt()

                    for (row in rowsAmount downTo 0) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                        ) {
                            val rowStart = 10 + (row) * columns
                            val rowEnd = (rowStart + 3).coerceAtMost(amount.coerceAtMost(16))

                            for (index in createSortedArray(rowStart..<rowEnd, columns)) {
                                val key = AVAILABLE_NUMBERS.keys.elementAt(index)
                                val icon = AVAILABLE_NUMBERS[key]!!
                                KeyboardButtonFilled(
                                    bModifier.weight(1f),
                                    icon,
                                    key,
                                    contentHeight,
                                ) { addDigit(key) }
                            }
                        }
                    }

                    for (int in createSortedArray(1..9, columns)) {
                        val key = AVAILABLE_NUMBERS.keys.elementAt(int)
                        val icon = AVAILABLE_NUMBERS[key]!!
                        KeyboardButtonLight(
                            bModifier.weight(1f),
                            icon,
                            key,
                            contentHeight,
                        ) { addDigit(key) }
                    }

                    KeyboardButtonLight(
                        bModifier.weight(1f),
                        IconPack.Key0,
                        Token.Digit._0,
                        contentHeight,
                    ) { addDigit(Token.Digit._0) }

                    KeyboardButtonTertiary(
                        bModifier.weight(2f),
                        IconPack.Backspace,
                        stringResource(R.string.delete_label),
                        contentHeight,
                        clearInput,
                    ) { deleteDigit() }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewConverterKeyboard() {
    DefaultKeyboard(
        modifier = Modifier.fillMaxSize(),
        addDigit = {},
        clearInput = {},
        deleteDigit = {},
        fractional = Token.PERIOD,
        middleZero = false,
        acButton = true,
        addBracket = {},
    )
}

@Preview
@Composable
private fun PreviewConverterKeyboardNumberBase() {
    NumberBaseKeyboard(
        modifier = Modifier.fillMaxSize(),
        addDigit = {},
        clearInput = {},
        deleteDigit = {},
        basis = BasicUnit.NumberBase.Hexadecimal,
    )
}

// So here's the problem. If we just go down from 3 downto 0 with columns 2, this results in:
// [3, 2]
// [1, 0]
// While this is correct, the ordering is incorrect. It should be
// [2, 3]
// [0, 1]
// TODO: Add support for rtl languages
internal fun createSortedArray(range: IntRange, columns: Int): List<Int> {
    val result = mutableListOf<Int>()
    val rows = ceil(range.count().toDouble() / columns.toDouble()).toInt()
    for (row in rows downTo 0) {
        for (column in 0 until columns) {
            val index = row * columns + column + range.first
            if (index <= range.last) {
                result.add(index)
            }
        }
    }

    return result
}
