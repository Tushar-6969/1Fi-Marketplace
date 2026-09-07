package com.example.fimarket_place

import com.example.fimarket_place.data.MarketplaceRepository
import com.example.fimarket_place.model.MarketplaceUiState
import com.example.fimarket_place.model.Offer
import com.example.fimarket_place.model.Product
import com.example.fimarket_place.ui.MarketplaceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketplaceViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProducts sets Success state when repository returns products`() = runTest {
        val mockRepo = object : MarketplaceRepository {
            override suspend fun getProducts(): Result<List<Product>> = Result.success(emptyList())
            override suspend fun getOffers(): Result<List<Offer>> = Result.success(emptyList())
            override suspend fun getCategories(): Result<List<String>> = Result.success(emptyList())
        }
        val viewModel = MarketplaceViewModel(mockRepo)
        
        // Initial state is Loading
        assertTrue(viewModel.uiState.value is MarketplaceUiState.Loading)
        
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Final state should be Success
        assertTrue(viewModel.uiState.value is MarketplaceUiState.Success)
    }
}
