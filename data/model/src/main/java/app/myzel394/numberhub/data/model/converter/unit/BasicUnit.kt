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

package app.myzel394.numberhub.data.model.converter.unit

import androidx.annotation.StringRes
import app.myzel394.numberhub.data.model.converter.UnitGroup
import java.math.BigDecimal

sealed interface BasicUnit {

    val id: String

    val group: UnitGroup

    @get:StringRes
    val displayName: Int

    @get:StringRes val shortName: Int

    val factor: BigDecimal

    interface NumberBase : BasicUnit {
        fun convert(unitTo: NumberBase, value: String): String

        companion object {
            val Hexadecimal = NumberBaseUnit(
                "hexadecimal",
                BigDecimal(16),
                UnitGroup.NUMBER_BASE,
                0,
                0,
            )
        }
    }

    interface Default : BasicUnit {
        val backward: Boolean

        fun convert(unitTo: Default, value: BigDecimal): BigDecimal
    }
}
