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

package app.myzel394.numberhub.data.converter

import android.content.Context
import android.util.Log
import app.myzel394.numberhub.core.base.Token
import app.myzel394.numberhub.data.common.isGreaterThan
import app.myzel394.numberhub.data.common.isLessThan
import app.myzel394.numberhub.data.common.trimZeros
import app.myzel394.numberhub.data.converter.collections.accelerationCollection
import app.myzel394.numberhub.data.converter.collections.angleCollection
import app.myzel394.numberhub.data.converter.collections.areaCollection
import app.myzel394.numberhub.data.converter.collections.currencyCollection
import app.myzel394.numberhub.data.converter.collections.dataCollection
import app.myzel394.numberhub.data.converter.collections.dataTransferCollection
import app.myzel394.numberhub.data.converter.collections.electrostaticCapacitance
import app.myzel394.numberhub.data.converter.collections.energyCollection
import app.myzel394.numberhub.data.converter.collections.flowRateCollection
import app.myzel394.numberhub.data.converter.collections.fluxCollection
import app.myzel394.numberhub.data.converter.collections.forceCollection
import app.myzel394.numberhub.data.converter.collections.fuelConsumptionCollection
import app.myzel394.numberhub.data.converter.collections.lengthCollection
import app.myzel394.numberhub.data.converter.collections.luminanceCollection
import app.myzel394.numberhub.data.converter.collections.massCollection
import app.myzel394.numberhub.data.converter.collections.numberBaseCollection
import app.myzel394.numberhub.data.converter.collections.powerCollection
import app.myzel394.numberhub.data.converter.collections.prefixCollection
import app.myzel394.numberhub.data.converter.collections.pressureCollection
import app.myzel394.numberhub.data.converter.collections.speedCollection
import app.myzel394.numberhub.data.converter.collections.temperatureCollection
import app.myzel394.numberhub.data.converter.collections.timeCollection
import app.myzel394.numberhub.data.converter.collections.torqueCollection
import app.myzel394.numberhub.data.converter.collections.volumeCollection
import app.myzel394.numberhub.data.converter.remote.CurrencyApi
import app.myzel394.numberhub.data.database.CurrencyRatesDao
import app.myzel394.numberhub.data.database.CurrencyRatesEntity
import app.myzel394.numberhub.data.database.UnitsDao
import app.myzel394.numberhub.data.database.UnitsEntity
import app.myzel394.numberhub.data.model.converter.UnitGroup
import app.myzel394.numberhub.data.model.converter.UnitsListSorting
import app.myzel394.numberhub.data.model.converter.unit.BasicUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import javax.inject.Inject

class UnitsRepositoryImpl @Inject constructor(
    private val unitsDao: UnitsDao,
    private val currencyRatesDao: CurrencyRatesDao,
    @ApplicationContext private val mContext: Context,
) {
    private val inMemory = lengthCollection +
        currencyCollection +
        massCollection +
        speedCollection +
        temperatureCollection +
        areaCollection +
        timeCollection +
        volumeCollection +
        dataCollection +
        pressureCollection +
        accelerationCollection +
        energyCollection +
        powerCollection +
        angleCollection +
        dataTransferCollection +
        fluxCollection +
        numberBaseCollection +
        electrostaticCapacitance +
        prefixCollection +
        forceCollection +
        torqueCollection +
        flowRateCollection +
        luminanceCollection +
        fuelConsumptionCollection

    suspend fun getById(id: String): BasicUnit = withContext(Dispatchers.Default) {
        return@withContext inMemory.first { it.id == id }
    }

    suspend fun getPairId(id: String): String = withContext(Dispatchers.IO) {
        val basedUnitPair = getUnitStats(id).pairedUnitId
        if (basedUnitPair != null) {
            return@withContext basedUnitPair
        }

        val inMemoryUnit = inMemory.first { it.id == id }
        val collection = inMemory.filter { it.group == inMemoryUnit.group }

        return@withContext when (inMemoryUnit.id) {
            // === === === Length === === ===
            UnitID.nanometer -> UnitID.micrometer
            UnitID.micrometer -> UnitID.millimeter
            UnitID.millimeter -> UnitID.centimeter

            UnitID.centimeter -> UnitID.inch
            UnitID.inch -> UnitID.centimeter
            UnitID.decimeter -> UnitID.centimeter
            UnitID.foot -> UnitID.meter
            UnitID.yard -> UnitID.meter
            UnitID.meter -> UnitID.foot

            UnitID.kilometer -> UnitID.mile
            UnitID.mile -> UnitID.kilometer
            UnitID.nautical_mile -> UnitID.kilometer

            UnitID.mercury_equatorial_radius -> UnitID.kilometer
            UnitID.mars_equatorial_radius -> UnitID.kilometer
            UnitID.venus_equatorial_radius -> UnitID.kilometer
            UnitID.earth_equatorial_radius -> UnitID.kilometer
            UnitID.neptune_equatorial_radius -> UnitID.kilometer
            UnitID.uranus_equatorial_radius -> UnitID.kilometer
            UnitID.saturn_equatorial_radius -> UnitID.kilometer
            UnitID.jupiter_equatorial_radius -> UnitID.kilometer
            UnitID.sun_equatorial_radius -> UnitID.kilometer

            UnitID.light_year -> UnitID.kilometer

            UnitID.parsec -> UnitID.light_year
            UnitID.kiloparsec -> UnitID.parsec
            UnitID.megaparsec -> UnitID.kiloparsec


            // === === === Mass === === ===
            UnitID.electron_mass_rest -> UnitID.atomic_mass_unit
            UnitID.atomic_mass_unit -> UnitID.electron_mass_rest

            UnitID.microgram -> UnitID.milligram
            UnitID.milligram -> UnitID.gram
            UnitID.grain -> UnitID.gram
            UnitID.carat -> UnitID.gram

            UnitID.gram -> UnitID.carat
            UnitID.ounce -> UnitID.gram
            UnitID.pound -> UnitID.kilogram
            UnitID.kilogram -> UnitID.pound

            UnitID.metric_ton -> UnitID.kilogram
            UnitID.imperial_ton -> UnitID.pound

            UnitID.mercury_mass -> UnitID.kilogram
            UnitID.mars_mass -> UnitID.kilogram
            UnitID.venus_mass -> UnitID.kilogram
            UnitID.earth_mass -> UnitID.kilogram
            UnitID.uranus_mass -> UnitID.kilogram
            UnitID.neptune_mass -> UnitID.kilogram
            UnitID.saturn_mass -> UnitID.kilogram
            UnitID.jupiter_mass -> UnitID.kilogram
            UnitID.sun_mass -> UnitID.kilogram


            // === === === Speed === === ===
            UnitID.millimeter_per_hour -> UnitID.millimeter_per_second
            UnitID.millimeter_per_second -> UnitID.millimeter_per_hour
            UnitID.millimeter_per_minute -> UnitID.millimeter_per_second
            UnitID.centimeter_per_hour -> UnitID.centimeter_per_second
            UnitID.centimeter_per_second -> UnitID.centimeter_per_hour
            UnitID.centimeter_per_minute -> UnitID.centimeter_per_second
            UnitID.meter_per_hour -> UnitID.meter_per_second
            UnitID.meter_per_second -> UnitID.meter_per_hour
            UnitID.meter_per_minute -> UnitID.meter_per_second

            UnitID.kilometer_per_hour -> UnitID.mile_per_hour
            UnitID.kilometer_per_second -> UnitID.mile_per_second
            UnitID.kilometer_per_minute -> UnitID.kilometer_per_second
            UnitID.mile_per_hour -> UnitID.kilometer_per_hour
            UnitID.mile_per_second -> UnitID.kilometer_per_second
            UnitID.mile_per_minute -> UnitID.mile_per_second

            UnitID.foot_per_hour -> UnitID.foot_per_second
            UnitID.foot_per_second -> UnitID.foot_per_hour
            UnitID.foot_per_minute -> UnitID.foot_per_second
            UnitID.yard_per_hour -> UnitID.yard_per_second
            UnitID.yard_per_second -> UnitID.yard_per_hour
            UnitID.yard_per_minute -> UnitID.yard_per_second

            UnitID.knot -> UnitID.kilometer_per_hour
            UnitID.mach -> UnitID.kilometer_per_hour
            UnitID.velocity_of_light_in_vacuum -> UnitID.kilometer_per_hour
            UnitID.earths_orbital_speed -> UnitID.kilometer_per_hour

            UnitID.cosmic_velocity_first -> UnitID.kilometer_per_hour
            UnitID.cosmic_velocity_second -> UnitID.kilometer_per_hour
            UnitID.cosmic_velocity_third -> UnitID.kilometer_per_hour


            // === === === Temperature === === ===
            UnitID.celsius -> UnitID.fahrenheit
            UnitID.fahrenheit -> UnitID.celsius
            UnitID.kelvin -> UnitID.celsius


            // === === === Area === === ===
            UnitID.square_micrometer -> UnitID.square_millimeter
            UnitID.square_millimeter -> UnitID.square_centimeter
            UnitID.square_centimeter -> UnitID.square_meter
            UnitID.square_decimeter -> UnitID.square_meter
            UnitID.square_meter -> UnitID.square_kilometer

            UnitID.square_kilometer -> UnitID.square_meter

            UnitID.square_inch -> UnitID.square_foot
            UnitID.square_foot -> UnitID.square_inch
            UnitID.square_yard -> UnitID.square_meter
            UnitID.square_mile -> UnitID.square_kilometer

            UnitID.acre -> UnitID.square_meter
            UnitID.hectare -> UnitID.square_meter
            UnitID.cent -> UnitID.square_meter


            // === === === Time === === ===
            UnitID.attosecond -> UnitID.nanosecond
            UnitID.nanosecond -> UnitID.microsecond
            UnitID.microsecond -> UnitID.millisecond
            UnitID.millisecond -> UnitID.second

            UnitID.jiffy -> UnitID.millisecond

            UnitID.second -> UnitID.millisecond
            UnitID.minute -> UnitID.second
            UnitID.hour -> UnitID.minute
            UnitID.day -> UnitID.hour
            UnitID.week -> UnitID.day


            // === === === Data === === ===
            // TODO: Add tibibyte, exibyte
            UnitID.bit -> UnitID.byte
            UnitID.byte -> UnitID.kilobyte
            UnitID.kilobyte -> UnitID.megabyte
            UnitID.megabyte -> UnitID.gigabyte
            UnitID.gigabyte -> UnitID.terabyte
            UnitID.terabyte -> UnitID.petabyte
            UnitID.petabyte -> UnitID.exabyte

            UnitID.kilobit -> UnitID.kilobyte
            UnitID.megabit -> UnitID.megabyte
            UnitID.gigabit -> UnitID.gigabyte
            UnitID.terabit -> UnitID.terabyte
            UnitID.petabit -> UnitID.petabyte
            UnitID.exabit -> UnitID.exabyte

            UnitID.kibibit -> UnitID.kilobyte
            UnitID.mebibit -> UnitID.megabyte
            UnitID.gibibit -> UnitID.gigabyte

            UnitID.kibibyte -> UnitID.kilobyte
            UnitID.mebibyte -> UnitID.megabyte
            UnitID.gibibyte -> UnitID.gigabyte


            // === === === Acceleration === === ===
            UnitID.millimeter_per_square_second -> UnitID.centimeter_per_square_second
            UnitID.centimeter_per_square_second -> UnitID.meter_per_square_second
            UnitID.decimeter_per_square_second -> UnitID.meter_per_square_second
            UnitID.meter_per_square_second -> UnitID.kilometer_per_square_second

            UnitID.mercury_surface_gravity -> UnitID.meter_per_square_second
            UnitID.mars_surface_gravity -> UnitID.meter_per_square_second
            UnitID.venus_surface_gravity -> UnitID.meter_per_square_second
            UnitID.uranus_surface_gravity -> UnitID.meter_per_square_second
            UnitID.earth_surface_gravity -> UnitID.meter_per_square_second
            UnitID.saturn_surface_gravity -> UnitID.meter_per_square_second
            UnitID.neptune_surface_gravity -> UnitID.meter_per_square_second
            UnitID.jupiter_surface_gravity -> UnitID.meter_per_square_second
            UnitID.sun_surface_gravity -> UnitID.meter_per_square_second


            // === === === Power === === ===
            UnitID.attowatt -> UnitID.watt
            UnitID.watt -> UnitID.kilowatt
            UnitID.kilowatt -> UnitID.watt
            UnitID.megawatt -> UnitID.kilowatt


            // === === === Angle === === ===
            UnitID.degree -> UnitID.radian
            UnitID.radian -> UnitID.degree


            // === === === Data Transfer === === ===
            UnitID.bit_per_second -> UnitID.byte_per_second
            UnitID.kilobit_per_second -> UnitID.kilobyte_per_second
            UnitID.megabit_per_second -> UnitID.megabyte_per_second
            UnitID.gigabit_per_second -> UnitID.gigabyte_per_second
            UnitID.terabit_per_second -> UnitID.terabyte_per_second
            UnitID.petabit_per_second -> UnitID.petabyte_per_second
            UnitID.exabit_per_second -> UnitID.exabyte_per_second

            UnitID.byte_per_second -> UnitID.kilobyte_per_second
            UnitID.kilobyte_per_second -> UnitID.megabyte_per_second
            UnitID.megabyte_per_second -> UnitID.gigabyte_per_second
            UnitID.gigabyte_per_second -> UnitID.megabyte_per_second
            UnitID.terabyte_per_second -> UnitID.gigabyte_per_second
            UnitID.petabyte_per_second -> UnitID.terabyte_per_second


            // === === === Fuel === === ===
            UnitID.kilometer_per_liter -> UnitID.mile_us_per_liter
            UnitID.mile_us_per_liter -> UnitID.kilometer_per_liter

            else ->
                (collection
                    .map { getById(it.id) to getUnitStats(it.id) }
                    .sortedByDescending { it.second.frequency }
                    .firstOrNull { it.second.isFavorite }?.first ?: collection.first()).id
        }
    }

    suspend fun incrementCounter(id: String) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    frequency = 1,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = basedUnit.isFavorite,
                    pairedUnitId = basedUnit.pairedUnitId,
                    frequency = basedUnit.frequency + 1,
                ),
            )
        }
    }

    suspend fun setPair(
        id: String,
        pairId: String,
    ) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    pairedUnitId = pairId,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = basedUnit.isFavorite,
                    pairedUnitId = pairId,
                    frequency = basedUnit.frequency,
                ),
            )
        }
    }

    suspend fun favorite(id: String) = withContext(Dispatchers.IO) {
        val basedUnit = unitsDao.getById(id)

        if (basedUnit == null) {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = id,
                    isFavorite = true,
                ),
            )
        } else {
            unitsDao.insertUnit(
                UnitsEntity(
                    unitId = basedUnit.unitId,
                    isFavorite = !basedUnit.isFavorite,
                    pairedUnitId = basedUnit.pairedUnitId,
                    frequency = basedUnit.frequency,
                ),
            )
        }
    }

    suspend fun filterUnits(
        query: String,
        unitGroups: List<UnitGroup>,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
    ): Map<UnitGroup, List<UnitSearchResultItem>> = withContext(Dispatchers.IO) {
        return@withContext filterUnitCollections(
            query = query,
            unitGroups = unitGroups,
            favoritesOnly = favoritesOnly,
            sorting = sorting,
        )
            .groupBy { it.basicUnit.group }
    }

    suspend fun filterUnitsAndBatchConvert(
        query: String,
        unitGroup: UnitGroup,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
        unitFromId: String,
        input: String?,
    ): Map<UnitGroup, List<UnitSearchResultItem>> = withContext(Dispatchers.IO) {
        val units = filterUnitCollections(
            query = query,
            unitGroups = listOf(unitGroup),
            favoritesOnly = favoritesOnly,
            sorting = sorting,
        )

        if (input == null) {
            return@withContext units.groupBy { it.basicUnit.group }
        }

        val unitWithConversions = try {
            when (unitGroup) {
                UnitGroup.CURRENCY -> {
                    val inputBD = BigDecimal(input)

                    val validCurrencyPairs = withContext(Dispatchers.IO) {
                        currencyRatesDao.getLatestRates(unitFromId)
                    }
                        .filter { it.pairUnitValue?.isGreaterThan(BigDecimal.ZERO) ?: false }

                    val validIds = validCurrencyPairs.map { it.pairUnitId }

                    units
                        .filter { it.basicUnit.id in validIds }
                        .map { unitTo ->
                            unitTo.basicUnit as BasicUnit.Default
                            val factor = validCurrencyPairs
                                .first { it.pairUnitId == unitTo.basicUnit.id }
                                .pairUnitValue
                            val conversion = inputBD.multiply(factor)

                            unitTo.copy(
                                conversion = DefaultBatchConvertResult(conversion),
                            )
                        }
                }

                UnitGroup.NUMBER_BASE -> {
                    val unitFrom = getById(unitFromId) as BasicUnit.NumberBase

                    units.map { unitTo ->
                        unitTo.basicUnit as BasicUnit.NumberBase
                        val conversion = unitFrom.convert(unitTo.basicUnit, input)

                        unitTo.copy(
                            conversion = NumberBaseBatchConvertResult(conversion),
                        )
                    }
                }

                else -> {
                    val unitFrom = getById(unitFromId) as BasicUnit.Default
                    val inputBD = BigDecimal(input)

                    units.map { unitTo ->
                        unitTo.basicUnit as BasicUnit.Default
                        val conversion = unitFrom.convert(unitTo.basicUnit, inputBD)

                        unitTo.copy(
                            conversion = DefaultBatchConvertResult(conversion),
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UnitsRepositoryImpl", "Failed to batch convert: $e")
            units
        }

        return@withContext unitWithConversions.groupBy { it.basicUnit.group }
    }

    suspend fun convertDefault(
        unitFromId: String,
        unitToId: String,
        value1: String,
        value2: String,
        formatTime: Boolean,
    ): ConverterResult = withContext(Dispatchers.Default) {
        val calculated: BigDecimal = try {
            // Calculate expression in first text field
            var calculated1 = Expression(value1.ifEmpty { Token.Digit._0 }).calculate()

            // Calculate expression in second text field
            if (unitFromId == UnitID.foot) {
                val calculatedInches = Expression(value2.ifEmpty { Token.Digit._0 }).calculate()
                // turn inches into feet so that it all comes down to converting from feet only
                val inches = getById(UnitID.inch) as BasicUnit.Default
                val feet = getById(UnitID.foot) as BasicUnit.Default
                val inchesConvertedToFeet = inches.convert(feet, calculatedInches)

                calculated1 += inchesConvertedToFeet
            }

            calculated1
        } catch (e: ExpressionException.DivideByZero) {
            return@withContext ConverterResult.Error.DivideByZero
        } catch (e: Exception) {
            return@withContext ConverterResult.Error.BadInput
        }

        return@withContext try {
            val unitFrom = getById(unitFromId) as BasicUnit.Default
            val unitTo = getById(unitToId) as BasicUnit.Default

            when {
                (unitFrom.group == UnitGroup.TIME) and (formatTime) ->
                    convertTime(unitFrom, calculated)

                unitTo.id == UnitID.foot ->
                    convertToFoot(unitFrom, unitTo, calculated)

                unitFrom.group == UnitGroup.CURRENCY ->
                    convertCurrencies(unitFromId, unitToId, calculated)

                else ->
                    convertDefault(unitFrom, unitTo, calculated)
            }
        } catch (e: Exception) {
            ConverterResult.Error.ConversionError
        }
    }

    suspend fun convertNumberBase(
        unitFromId: String,
        unitToId: String,
        value: String,
    ): ConverterResult = withContext(Dispatchers.Default) {
        return@withContext try {
            val unitFrom = getById(unitFromId) as BasicUnit.NumberBase
            val unitTo = getById(unitToId) as BasicUnit.NumberBase
            val conversion = unitFrom.convert(unitTo, value)

            ConverterResult.NumberBase(conversion)
        } catch (error: Exception) {
            Log.e("UnitsRepositoryImpl", "Failed to convert number base $unitFromId -> $unitToId: $error")
            error.printStackTrace()
            ConverterResult.Error.ConversionError
        }
    }

    private suspend fun getUnitStats(id: String): UnitsEntity = withContext(Dispatchers.IO) {
        unitsDao.getById(id) ?: UnitsEntity(unitId = id)
    }

    private fun convertDefault(
        unitFrom: BasicUnit.Default,
        unitTo: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.Default = ConverterResult.Default(unitFrom.convert(unitTo, value), value)

    internal fun convertTime(
        unitFrom: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.Time {
        val input = value.multiply(unitFrom.factor)

        val negative = input < BigDecimal.ZERO
        val inputAbs = input.abs()

        if (inputAbs.isLessThan(attosecondBasicUnit)) {
            return ConverterResult.Time(
                negative = negative,
                day = BigDecimal.ZERO,
                hour = BigDecimal.ZERO,
                minute = BigDecimal.ZERO,
                second = BigDecimal.ZERO,
                millisecond = BigDecimal.ZERO,
                microsecond = BigDecimal.ZERO,
                nanosecond = BigDecimal.ZERO,
                attosecond = inputAbs,
            )
        }

        if (inputAbs.isLessThan(nanosecondBasicUnit)) {
            return ConverterResult.Time(
                negative = negative,
                day = BigDecimal.ZERO,
                hour = BigDecimal.ZERO,
                minute = BigDecimal.ZERO,
                second = BigDecimal.ZERO,
                millisecond = BigDecimal.ZERO,
                microsecond = BigDecimal.ZERO,
                nanosecond = BigDecimal.ZERO,
                attosecond = inputAbs.trimZeros(),
            )
        }

        // DAY
        var division = inputAbs.divideAndRemainder(dayBasicUnit)
        val day = division.component1().setScale(0, RoundingMode.HALF_EVEN)
        var remainingSeconds = division.component2().setScale(0, RoundingMode.HALF_EVEN)

        division = remainingSeconds.divideAndRemainder(hourBasicUnit)
        val hour = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(minuteBasicUnit)
        val minute = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(secondBasicUnit)
        val second = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(millisecondBasicUnit)
        val millisecond = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(microsecondBasicUnit)
        val microsecond = division.component1()
        remainingSeconds = division.component2()

        division = remainingSeconds.divideAndRemainder(nanosecondBasicUnit)
        val nanosecond = division.component1()
        remainingSeconds = division.component2()

        val attosecond = remainingSeconds

        return ConverterResult.Time(
            negative = negative,
            day = day,
            hour = hour,
            minute = minute,
            second = second,
            millisecond = millisecond,
            microsecond = microsecond,
            nanosecond = nanosecond,
            attosecond = attosecond,
        )
    }

    private suspend fun convertToFoot(
        unitFrom: BasicUnit.Default,
        unitTo: BasicUnit.Default,
        value: BigDecimal,
    ): ConverterResult.FootInch = ConverterResult.FootInch.fromBigDecimal(
        input = unitFrom.convert(unitTo, value),
        footUnit = unitTo,
        inchUnit = getById(UnitID.inch) as BasicUnit.Default,
    )

    private suspend fun convertCurrencies(
        unitFromId: String,
        unitToId: String,
        value: BigDecimal,
    ): ConverterResult = withContext(Dispatchers.IO) {
        refreshCurrencyRates(unitFromId, unitToId)

        val latestRate = currencyRatesDao.getLatestRate(unitFromId, unitToId)
        if (latestRate?.pairUnitValue == null) return@withContext ConverterResult.Error.Currency

        val conversion = value.multiply(latestRate.pairUnitValue)

        return@withContext ConverterResult.Default(conversion, value)
    }

    private suspend fun filterUnitCollections(
        query: String,
        unitGroups: List<UnitGroup>,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
    ): Sequence<UnitSearchResultItem> = withContext(Dispatchers.IO) {
        var units = inMemory
            .filter { it.group in unitGroups }
            .map { UnitSearchResultItem(it, getUnitStats(it.id), null) }
            .asSequence()

        if (favoritesOnly) {
            units = units.filter { it.stats.isFavorite }
        }

        units = when (sorting) {
            UnitsListSorting.USAGE -> units.sortedByDescending { it.stats.frequency }
            UnitsListSorting.ALPHABETICAL -> units.sortedBy { mContext.getString(it.basicUnit.displayName) }
            UnitsListSorting.SCALE_ASC -> units.sortedBy { it.basicUnit.factor }
            UnitsListSorting.SCALE_DESC -> units.sortedByDescending { it.basicUnit.factor }
            else -> units
        }

        units = if (query.isEmpty()) {
            units.sortedByDescending { it.stats.isFavorite }
        } else {
            units.filterByLev(query, mContext)
        }
        return@withContext units
    }

    private suspend fun refreshCurrencyRates(unitFromId: String, unitToId: String) = withContext(Dispatchers.IO) {
        val latestUpdateDate = currencyRatesDao.getLatestRateTimeStamp(unitFromId)
        val currentDate = LocalDate.now().toEpochDay()

        if (latestUpdateDate != currentDate) {
            // Need to update cache needed
            try {
                val conversions = CurrencyApi.service.getCurrencyPairs(unitFromId)
                val rates = conversions.currency
                    .map { (pairId, pairValue) ->
                        CurrencyRatesEntity(
                            baseUnitId = unitFromId,
                            date = currentDate,
                            pairUnitId = pairId,
                            pairUnitValue = BigDecimal.valueOf(pairValue),
                        )
                    }
                currencyRatesDao.insertRates(rates)
            } catch (e: Exception) {
                Log.w("UnitsRepositoryImpl", "Updating currency rates failed: $e");
                // If fails, try to get the currency `unitToId` and calculate the rate
                // example:

                val latestReverseData = currencyRatesDao.getLatestRate(unitToId, unitFromId);

                if (latestReverseData != null) {
                    val reverseRate = BigDecimal.ONE.divide(latestReverseData.pairUnitValue, 10, RoundingMode.HALF_EVEN)

                    Log.d("UnitsRepositoryImpl", "Updated currency via reverse rate: $reverseRate");

                    currencyRatesDao.insertRates(
                        listOf(
                            CurrencyRatesEntity(
                                baseUnitId = unitFromId,
                                date = currentDate,
                                pairUnitId = unitToId,
                                pairUnitValue = reverseRate,
                            ),
                        ),
                    )
                }
            }
        }
    }
}

private val dayBasicUnit by lazy { BigDecimal("86400000000000000000000") }
private val hourBasicUnit by lazy { BigDecimal("3600000000000000000000") }
private val minuteBasicUnit by lazy { BigDecimal("60000000000000000000") }
private val secondBasicUnit by lazy { BigDecimal("1000000000000000000") }
private val millisecondBasicUnit by lazy { BigDecimal("1000000000000000") }
private val microsecondBasicUnit by lazy { BigDecimal("1000000000000") }
private val nanosecondBasicUnit by lazy { BigDecimal("1000000000") }
private val attosecondBasicUnit by lazy { BigDecimal("1") }
