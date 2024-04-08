package com.rayadams.roomdbexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rayadams.roomdbexample.navigation.CustomNavigator
import com.rayadams.roomdbexample.navigation.NavigationPath
import com.rayadams.roomdbexample.ui.theme.RoomDBExampleTheme
import com.rayadams.roomdbexample.views.ContactsView
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
        NavHost(navController = navController, startDestination = NavigationPath.CONTACTS_VIEW) {
            composable(NavigationPath.CONTACTS_VIEW) {
                ContactsView()
            }
        }
    }
}
