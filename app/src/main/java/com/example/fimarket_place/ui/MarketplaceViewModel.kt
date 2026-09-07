package com.example.fimarket_place.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fimarket_place.data.MarketplaceRepository
import com.example.fimarket_place.data.MockMarketplaceRepository
import com.example.fimarket_place.model.MarketplaceUiState
import com.example.fimarket_place.model.Offer
import com.example.fimarket_place.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val repository: MarketplaceRepository = MockMarketplaceRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarketplaceUiState>(MarketplaceUiState.Loading)
    val uiState: StateFlow<MarketplaceUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allProducts: List<Product> = emptyList()
    private var allOffers: List<Offer> = emptyList()
    private var allCategories: List<String> = emptyList()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = MarketplaceUiState.Loading
            
            val productsResult = repository.getProducts()
            val offersResult = repository.getOffers()
            val categoriesResult = repository.getCategories()

            if (productsResult.isSuccess && offersResult.isSuccess && categoriesResult.isSuccess) {
                allProducts = productsResult.getOrNull() ?: emptyList()
                allOffers = offersResult.getOrNull() ?: emptyList()
                allCategories = categoriesResult.getOrNull() ?: emptyList()
                
                updateUiState()
            } else {
                _uiState.value = MarketplaceUiState.Error("Failed to load marketplace data")
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        updateUiState()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        updateUiState()
    }

    private fun updateUiState() {
        var filtered = allProducts
        
        // Filter by category
        if (_selectedCategory.value != "All") {
            filtered = filtered.filter { it.category == _selectedCategory.value }
        }
        
        // Filter by search query
        if (_searchQuery.value.isNotEmpty()) {
            filtered = filtered.filter { 
                it.name.contains(_searchQuery.value, ignoreCase = true) || 
                it.description.contains(_searchQuery.value, ignoreCase = true) 
            }
        }
        
        _uiState.value = MarketplaceUiState.Success(
            products = filtered,
            offers = allOffers,
            categories = allCategories
        )
    }
}
