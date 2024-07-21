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

package io.github.sadellie.evaluatto

import org.junit.Test

class ExpressionComplexTest {

    @Test
    fun expression1() = assertExpr("94×π×89×cos(0.5)−3!÷9^(2)×√8", "23064.9104578494")

    @Test
    fun expression2() = assertExpr("√(25)×2+10÷2", "15")

    @Test
    fun expression3() = assertExpr("(3+4)×(5−2)", "21")

    @Test
    fun expression4() = assertExpr("8÷4+2×3", "8")

    @Test
    fun expression5() = assertExpr("2^3+4^2−5×6", "-6")

    @Test
    fun expression6() = assertExpr("(10−2)^2÷8+3×2", "14")

    @Test
    fun expression7() = assertExpr("7!÷3!−5!÷2!", "780")

    @Test
    fun expression8() = assertExpr("(2^2+3^3)÷5−√(16)×2", "-1.8")

    @Test
    fun expression9() = assertExpr("10×log(100)+2^4−3^2", "27")

    @Test
    fun expression10() = assertExpr("sin(π÷3)×cos(π÷6)+tan(π÷4)−√3", "0.017949192431123")

    @Test
    fun expression11() = assertExpr("2^6−2^5+2^4−2^3+2^−2^1+2^0", "41.25")

    @Test
    fun expression12() = assertExpr("2×(3+4)×(5−2)÷6", "7")

    @Test
    fun shouldDivideRootAndDivideInCorrectOrder() = assertExpr("(√9)÷6", "0.5")

    @Test
    fun singleNumber() = assertExpr("42", "42")

    @Test(expected = NumberFormatException::class)
    fun divisionByZero() = assertExpr("1÷0", "")

    @Test(expected = NumberFormatException::class)
    fun invalidExpressionMissingOperand() = assertExpr("42+", "")

    @Test
    fun largeNumbers() = assertExpr("9999999999*9999999999", "99999999980000000001")

    @Test
    fun highPrecision() = assertExpr("1÷3", "0.3333333333")

    @Test
    fun deeplyNestedParentheses() = assertExpr("((((((42))))))", "42")

    @Test
    fun constantsAndFunctions() = assertExpr("π+e", "${Math.PI + Math.E}")

    @Test
    fun edgeMathematicalCases() =
        assertExpr("0^0", "1") // Depending on the mathematical interpretation used

    @Test
    fun trigonometricLimits() = assertExpr("sin(0)", "0")

    @Test
    fun sinAtPi() = assertExpr("sin(π)", "0")

    @Test
    fun sinAtHalfPi() = assertExpr("sin(π÷2)", "1")

    @Test
    fun cosAtPi() = assertExpr("cos(π)", "-1")

    @Test
    fun cosAtHalfPi() = assertExpr("cos(π÷2)", "0")

    @Test
    fun cosAtPiOverThree() = assertExpr("cos(π÷3)", "0.5")
}
