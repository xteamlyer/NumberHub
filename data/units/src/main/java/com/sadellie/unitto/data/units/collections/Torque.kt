/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.DefaultUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.R
import java.math.BigDecimal

val torqueCollection: List<AbstractUnit> by lazy {
    listOf(
        DefaultUnit(MyUnitIDS.dyne_millimeter,           BigDecimal.valueOf(1),                      UnitGroup.TORQUE,   R.string.dyne_millimeter,           R.string.dyne_millimeter_short),
        DefaultUnit(MyUnitIDS.dyne_centimeter,           BigDecimal.valueOf(10),                     UnitGroup.TORQUE,   R.string.dyne_centimeter,           R.string.dyne_centimeter_short),
        DefaultUnit(MyUnitIDS.dyne_meter,                BigDecimal.valueOf(1000),                   UnitGroup.TORQUE,   R.string.dyne_meter,                R.string.dyne_meter_short),
        DefaultUnit(MyUnitIDS.newton_millimeter,         BigDecimal.valueOf(100000),                 UnitGroup.TORQUE,   R.string.newton_millimeter,         R.string.newton_millimeter_short),
        DefaultUnit(MyUnitIDS.newton_centimeter,         BigDecimal.valueOf(1000000),                UnitGroup.TORQUE,   R.string.newton_centimeter,         R.string.newton_centimeter_short),
        DefaultUnit(MyUnitIDS.newton_meter,              BigDecimal.valueOf(100000000),              UnitGroup.TORQUE,   R.string.newton_meter,              R.string.newton_meter_short),
        DefaultUnit(MyUnitIDS.kilonewton_meter,          BigDecimal.valueOf(100000000000),           UnitGroup.TORQUE,   R.string.kilonewton_meter,          R.string.kilonewton_meter_short),
        DefaultUnit(MyUnitIDS.gram_force_millimeter,     BigDecimal.valueOf(980.665),                UnitGroup.TORQUE,   R.string.gram_force_millimeter,     R.string.gram_force_millimeter_short),
        DefaultUnit(MyUnitIDS.gram_force_centimeter,     BigDecimal.valueOf(9806.65),                UnitGroup.TORQUE,   R.string.gram_force_centimeter,     R.string.gram_force_centimeter_short),
        DefaultUnit(MyUnitIDS.kilogram_force_millimeter, BigDecimal.valueOf(980665),                 UnitGroup.TORQUE,   R.string.kilogram_force_millimeter, R.string.kilogram_force_millimeter_short),
        DefaultUnit(MyUnitIDS.gram_force_meter,          BigDecimal.valueOf(980665),                 UnitGroup.TORQUE,   R.string.gram_force_meter,          R.string.gram_force_meter_short),
        DefaultUnit(MyUnitIDS.kilogram_force_centimeter, BigDecimal.valueOf(9806650),                UnitGroup.TORQUE,   R.string.kilogram_force_centimeter, R.string.kilogram_force_centimeter_short),
        DefaultUnit(MyUnitIDS.kilogram_force_meter,      BigDecimal.valueOf(980665000),              UnitGroup.TORQUE,   R.string.kilogram_force_meter,      R.string.kilogram_force_meter_short),
        DefaultUnit(MyUnitIDS.ounce_force_foot,          BigDecimal.valueOf(8473862.4),              UnitGroup.TORQUE,   R.string.ounce_force_foot,          R.string.ounce_force_foot_short),
        DefaultUnit(MyUnitIDS.ounce_force_inch,          BigDecimal.valueOf(706155.2),               UnitGroup.TORQUE,   R.string.ounce_force_inch,          R.string.ounce_force_inch_short),
        DefaultUnit(MyUnitIDS.pound_force_foot,          BigDecimal.valueOf(135581800),              UnitGroup.TORQUE,   R.string.pound_force_foot,          R.string.pound_force_foot_short),
        DefaultUnit(MyUnitIDS.pound_force_inch,          BigDecimal.valueOf(1.1298483333333334E7),   UnitGroup.TORQUE,   R.string.pound_force_inch,          R.string.pound_force_inch_short),
    )
}
