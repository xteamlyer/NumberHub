/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package app.myzel394.numberhub.data.model.repository

import app.myzel394.numberhub.data.model.converter.UnitGroup
import app.myzel394.numberhub.data.model.converter.UnitsListSorting
import app.myzel394.numberhub.data.model.userprefs.AboutPreferences
import app.myzel394.numberhub.data.model.userprefs.AddSubtractPreferences
import app.myzel394.numberhub.data.model.userprefs.AppPreferences
import app.myzel394.numberhub.data.model.userprefs.BodyMassPreferences
import app.myzel394.numberhub.data.model.userprefs.CalculatorPreferences
import app.myzel394.numberhub.data.model.userprefs.ConverterPreferences
import app.myzel394.numberhub.data.model.userprefs.DisplayPreferences
import app.myzel394.numberhub.data.model.userprefs.FormattingPreferences
import app.myzel394.numberhub.data.model.userprefs.GeneralPreferences
import app.myzel394.numberhub.data.model.userprefs.StartingScreenPreferences
import app.myzel394.numberhub.data.model.userprefs.UnitGroupsPreferences
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val appPrefs: Flow<AppPreferences>
    val generalPrefs: Flow<GeneralPreferences>
    val calculatorPrefs: Flow<CalculatorPreferences>
    val converterPrefs: Flow<ConverterPreferences>
    val displayPrefs: Flow<DisplayPreferences>
    val formattingPrefs: Flow<FormattingPreferences>
    val unitGroupsPrefs: Flow<UnitGroupsPreferences>
    val addSubtractPrefs: Flow<AddSubtractPreferences>
    val bodyMassPrefs: Flow<BodyMassPreferences>
    val aboutPrefs: Flow<AboutPreferences>
    val startingScreenPrefs: Flow<StartingScreenPreferences>

    suspend fun updateDigitsPrecision(precision: Int)

    suspend fun updateFormatterSymbols(grouping: String, fractional: String)

    suspend fun updateOutputFormat(outputFormat: Int)

    suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String)

    suspend fun updateThemingMode(themingMode: ThemingMode)

    suspend fun updateDynamicTheme(enabled: Boolean)

    suspend fun updateAmoledTheme(enabled: Boolean)

    suspend fun updateCustomColor(color: Long)

    suspend fun updateMonetMode(monetMode: MonetMode)

    suspend fun updateStartingScreen(startingScreen: String)

    suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>)

    suspend fun addShownUnitGroup(unitGroup: UnitGroup)

    suspend fun removeShownUnitGroup(unitGroup: UnitGroup)

    suspend fun updateLastReadChangelog(value: String)

    suspend fun updateVibrations(enabled: Boolean)

    suspend fun updateMiddleZero(enabled: Boolean)

    suspend fun updateToolsExperiment(enabled: Boolean)

    suspend fun updateRadianMode(radianMode: Boolean)

    suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean)

    suspend fun updateUnitConverterFormatTime(enabled: Boolean)

    suspend fun updateUnitConverterSorting(sorting: UnitsListSorting)

    suspend fun updateSystemFont(enabled: Boolean)

    suspend fun updatePartialHistoryView(enabled: Boolean)

    suspend fun updateAcButton(enabled: Boolean)

    suspend fun updateAdditionalButtons(enabled: Boolean)

    suspend fun updateInverseMode(enabled: Boolean)
}
