/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package app.myzel394.numberhub.feature.converter

import androidx.compose.ui.text.input.TextFieldValue
import app.myzel394.numberhub.core.base.FormatterSymbols
import app.myzel394.numberhub.data.converter.UnitSearchResultItem
import app.myzel394.numberhub.data.model.converter.UnitGroup
import app.myzel394.numberhub.data.model.converter.UnitsListSorting
import app.myzel394.numberhub.data.model.converter.unit.BasicUnit

internal sealed class UnitSelectorUIState {
    data object Loading : UnitSelectorUIState()

    data class UnitFrom(
        val query: TextFieldValue,
        val unitFromId: String,
        val shownUnitGroups: List<UnitGroup>,
        val showFavoritesOnly: Boolean,
        val units: Map<UnitGroup, List<UnitSearchResultItem>>,
        val selectedUnitGroup: UnitGroup?,
        val sorting: UnitsListSorting,
    ) : UnitSelectorUIState()

    data class UnitTo(
        val query: TextFieldValue,
        val unitFrom: BasicUnit,
        val unitTo: BasicUnit,
        val showFavoritesOnly: Boolean,
        val units: Map<UnitGroup, List<UnitSearchResultItem>>,
        val input: String?,
        val sorting: UnitsListSorting,
        val scale: Int,
        val outputFormat: Int,
        val formatterSymbols: FormatterSymbols,
    ) : UnitSelectorUIState()
}
