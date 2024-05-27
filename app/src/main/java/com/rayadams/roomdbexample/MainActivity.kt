package com.rayadams.roomdbexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadams.roomdbexample.navigation.CustomNavigator
import com.rayadams.roomdbexample.navigation.NavigationParams
import com.rayadams.roomdbexample.navigation.NavigationPath
import com.rayadams.roomdbexample.ui.theme.RoomDBExampleTheme
import com.rayadams.roomdbexample.views.AddContactView
import com.rayadams.roomdbexample.views.ContactsView
import com.rayadams.roomdbexample.views.EditContactView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navHelper: CustomNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBExampleTheme {

                val navController: NavHostController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            navHelper.navActions.collect { navigatorState ->
                                navigatorState.parcelableArguments.forEach { arg ->
                                    navController.currentBackStackEntry?.arguments?.putParcelable(
                                        arg.key,
                                        arg.value
                                    )
                                }
                                navHelper.runNavigationCommand(navigatorState, navController)

                            }
                        }
                    }
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationView(navController)
                }
            }
        }
    }

    @Composable
    fun NavigationView(navController: NavHostController) {
        val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
        val slideTime = 300
        val slideInHorizontally =
            slideInHorizontally(initialOffsetX = { if (isLtr) it else -it }, animationSpec = tween(slideTime))
        val slideOutHorizontally =
            slideOutHorizontally(targetOffsetX = { if (isLtr) it else -it }, animationSpec = tween(slideTime))

        NavHost(
            navController = navController, startDestination = NavigationPath.CONTACTS_VIEW,
            enterTransition = { slideInHorizontally },
            exitTransition = { slideOutHorizontally },
        ) {
            composable(
                NavigationPath.CONTACTS_VIEW
            ) {
                ContactsView()
            }
            composable(NavigationPath.ADD_CONTACTS_VIEW,
                enterTransition = { slideInHorizontally },
                popEnterTransition = { null },
                popExitTransition = { slideOutHorizontally }) {
                AddContactView()
            }
            composable(
                NavigationPath.EDIT_CONTACT_VIEW_ID,
                arguments = listOf(navArgument(NavigationParams.CONTACT_ID) { type = NavType.IntType }),
                enterTransition = { slideInHorizontally },
                popEnterTransition = { null },
                popExitTransition = { slideOutHorizontally }
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    NavigationParams.CONTACT_ID,
                    navController.currentBackStackEntry?.arguments?.getInt(NavigationParams.CONTACT_ID)
                )
                EditContactView()
            }
        }
    }
}
