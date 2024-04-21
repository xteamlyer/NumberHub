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

package app.myzel394.numberhub.feature.settings

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myzel394.numberhub.core.base.BuildConfig
import app.myzel394.numberhub.data.backup.BackupManager
import app.myzel394.numberhub.data.common.stateIn
import app.myzel394.numberhub.data.database.CurrencyRatesDao
import app.myzel394.numberhub.data.database.UnittoDatabase
import app.myzel394.numberhub.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val currencyRatesDao: CurrencyRatesDao,
    private val database: UnittoDatabase,
) : ViewModel() {
    private val _showErrorToast = MutableSharedFlow<Boolean>()
    val showErrorToast = _showErrorToast.asSharedFlow()

    private val backupInProgress = MutableStateFlow(false)
    private var backupJob: Job? = null

    val uiState = combine(
        userPrefsRepository.generalPrefs,
        currencyRatesDao.size(),
        backupInProgress,
    ) { prefs, cacheSize, backupInProgress ->
        SettingsUIState.Ready(
            enableVibrations = prefs.enableVibrations,
            cacheSize = cacheSize,
            backupInProgress = backupInProgress,
            showUpdateChangelog = prefs.lastReadChangelog != BuildConfig.VERSION_CODE,
        )
    }
        .stateIn(viewModelScope, SettingsUIState.Loading)

    fun backup(
        context: Context,
        uri: Uri,
    ) {
        backupJob?.cancel()
        backupJob = viewModelScope.launch(Dispatchers.IO) {
            backupInProgress.update { true }
            try {
                BackupManager().backup(context, uri, database)
            } catch (e: Exception) {
                _showErrorToast.emit(true)
                Log.e(TAG, "$e")
            }
            backupInProgress.update { false }
        }
    }

    fun restore(
        context: Context,
        uri: Uri,
    ) {
        backupJob?.cancel()
        backupJob = viewModelScope.launch(Dispatchers.IO) {
            backupInProgress.update { true }
            try {
                BackupManager().restore(context, uri, database)
            } catch (e: Exception) {
                _showErrorToast.emit(true)
                Log.e(TAG, "$e")
            }
            backupInProgress.update { false }
        }
    }

    /**
     * @see UserPreferencesRepository.updateLastReadChangelog
     */
    fun updateLastReadChangelog(value: String) = viewModelScope.launch {
        userPrefsRepository.updateLastReadChangelog(value)
    }

    /**
     * @see UserPreferencesRepository.updateVibrations
     */
    fun updateVibrations(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateVibrations(enabled)
    }

    fun clearCache() = viewModelScope.launch(Dispatchers.IO) {
        currencyRatesDao.clear()
    }
}

private const val TAG = "SettingsViewModel"
