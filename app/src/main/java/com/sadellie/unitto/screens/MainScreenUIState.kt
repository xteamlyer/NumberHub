package com.sadellie.unitto.screens

import com.sadellie.unitto.data.KEY_0

/**
 * Represents current state of the MainScreen
 *
 * @property inputValue Current input value
 * @property resultValue Current output value
 * @property deleteButtonEnabled Delete last symbol from input button state
 * @property dotButtonEnabled Add dot to input button state
 * @property negateButtonEnabled Switch input between positive and negative button state
 * @property isLoadingDatabase Whether we are loading data from Database. Need on app launch, once
 * we are done loading list on Units list can be sorted by usage properly/
 * @property isLoadingNetwork Whether we are loading data from network
 * @property showError Whether there was an error while loading data from network
 */
data class MainScreenUIState(
    var inputValue: String = KEY_0,
    var resultValue: String = KEY_0,
    var deleteButtonEnabled: Boolean = false,
    var dotButtonEnabled: Boolean = true,
    var negateButtonEnabled: Boolean = false,
    var isLoadingDatabase: Boolean = true,
    var isLoadingNetwork: Boolean = false,
    var showError: Boolean = false,
)
