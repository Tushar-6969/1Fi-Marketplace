package com.example.fimarket_place.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fimarket_place.model.MarketplaceUiState
import com.example.fimarket_place.model.Product
import com.example.fimarket_place.ui.MarketplaceViewModel
import com.example.fimarket_place.ui.components.*

@Composable
fun ShopScreen(
    viewModel: MarketplaceViewModel = viewModel(),
    onProductClick: (Product) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(2) } 
    val tabs = listOf("Top Brands", "Nearby Stores", "1Fi Marketplace")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBanner()
        
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (selectedTabIndex) {
            0 -> PlaceholderTab(
                title = "Top Brands", 
                subtitle = "Shop using 1Fi at your favorite brands",
                buttonText = "Explore Brands"
            )
            1 -> PlaceholderTab(
                title = "Nearby Stores", 
                subtitle = "Discover stores near you accepting 1Fi",
                buttonText = "Find Stores"
            )
            2 -> MarketplaceTab(uiState, viewModel, selectedCategory, searchQuery, onProductClick)
        }
    }
}

@Composable
fun MarketplaceTab(
    uiState: MarketplaceUiState,
    viewModel: MarketplaceViewModel,
    selectedCategory: String,
    searchQuery: String,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { 
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) }
            ) 
        }

        when (uiState) {
            is MarketplaceUiState.Loading -> {
                item { LoadingView() }
            }
            is MarketplaceUiState.Success -> {
                if (searchQuery.isEmpty()) {
                    item { 
                        FeaturedOffersCarousel(offers = uiState.offers)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                
                item {
                    CategorySelector(
                        categories = uiState.categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.selectCategory(it) }
                    )
                }

                item {
                    Text(
                        text = if (searchQuery.isEmpty()) "1Fi Marketplace" else "Search Results",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                if (uiState.products.isEmpty()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("No products found matching \"$searchQuery\"", color = Color.Gray)
                        }
                    }
                } else {
                    items(uiState.products) { product ->
                        ProductCard(product = product, onClick = { onProductClick(product) })
                    }
                }
                
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
            is MarketplaceUiState.Error -> {
                item { ErrorView(message = uiState.message, onRetry = { viewModel.loadProducts() }) }
            }
        }
    }
}

@Composable
fun PlaceholderTab(title: String, subtitle: String, buttonText: String) {
    EmptyStateView(
        title = title,
        subtitle = subtitle,
        buttonText = buttonText,
        onButtonClick = { /* Action */ }
    )
}
