package com.example.fimarket_place

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fimarket_place.model.MarketplaceUiState
import com.example.fimarket_place.ui.MarketplaceViewModel
import com.example.fimarket_place.ui.screens.MarketplaceDetailsScreen
import com.example.fimarket_place.ui.screens.ShopScreen
import com.example.fimarket_place.ui.theme.Fimarket_placeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fimarket_placeTheme {
                MainApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Shop : Screen("shop", "Shop", Icons.Default.Storefront)
    object EmiDues : Screen("emi_dues", "EMI Dues", Icons.Default.Receipt)
    object Limit : Screen("limit", "Limit", Icons.Default.Assessment)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Screen.Home,
        Screen.Shop,
        Screen.EmiDues,
        Screen.Limit,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title, fontSize = 10.sp) },
                        selected = currentDestination?.route?.startsWith(screen.route) == true,
                        onClick = {
                            if (screen == Screen.Shop) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Shop.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { PlaceholderScreen("Home") }
            composable(Screen.Shop.route) {
                ShopScreen(onProductClick = { product ->
                    navController.navigate("details/${product.id}")
                })
            }
            composable(Screen.EmiDues.route) { PlaceholderScreen("EMI Dues") }
            composable(Screen.Limit.route) { PlaceholderScreen("Limit") }
            composable(Screen.Profile.route) { PlaceholderScreen("Profile") }
            
            composable("details/{productId}") { backStackEntry ->
                val viewModel: MarketplaceViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                
                val productId = backStackEntry.arguments?.getString("productId")
                val product = (uiState as? MarketplaceUiState.Success)?.products?.find { it.id == productId }
                
                if (product != null) {
                    MarketplaceDetailsScreen(product = product, onBack = { navController.popBackStack() })
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Product not found")
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$name Screen")
    }
}
