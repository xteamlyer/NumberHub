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

package app.myzel394.numberhub.feature.timezone.navigation

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import app.myzel394.numberhub.core.ui.common.EmptyScreen
import app.myzel394.numberhub.core.ui.model.DrawerItem
import app.myzel394.numberhub.core.ui.unittoComposable
import app.myzel394.numberhub.core.ui.unittoNavigation
import app.myzel394.numberhub.feature.timezone.AddTimeZoneRoute
import app.myzel394.numberhub.feature.timezone.TimeZoneRoute
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val graph = DrawerItem.TimeZones.graph
private val start = DrawerItem.TimeZones.start
private const val ADD_TIME_ZONE_ROUTE = "ADD_TIME_ZONE_ROUTE"
private const val USER_TIME_ARG = "USER_TIME_ARG"

private fun NavController.navigateToAddTimeZone(
    userTime: ZonedDateTime,
) {
    val formattedTime = userTime
        .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        .replace("/", "|") // this is so wrong

    navigate("$ADD_TIME_ZONE_ROUTE/$formattedTime")
}

fun NavGraphBuilder.timeZoneGraph(
    openDrawer: () -> Unit,
    navController: NavHostController,
) {
    unittoNavigation(
        startDestination = start,
        route = graph,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://app.myzel394.numberhub/$graph" },
        ),
    ) {
        unittoComposable(start) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                EmptyScreen()
                return@unittoComposable
            }

            TimeZoneRoute(
                openDrawer = openDrawer,
                navigateToAddTimeZone = navController::navigateToAddTimeZone,
            )
        }

        unittoComposable(
            route = "$ADD_TIME_ZONE_ROUTE/{$USER_TIME_ARG}",
            arguments = listOf(
                navArgument(USER_TIME_ARG) {
                    defaultValue = null
                    nullable = true
                    type = NavType.StringType
                },
            ),
        ) { stackEntry ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                EmptyScreen()
                return@unittoComposable
            }

            val userTime = stackEntry.arguments
                ?.getString(USER_TIME_ARG)
                ?.replace("|", "/") // war crime, don't look
                ?.let { ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME) }
                ?: ZonedDateTime.now()

            AddTimeZoneRoute(
                navigateUp = navController::navigateUp,
                userTime = userTime,
            )
        }
    }
}
