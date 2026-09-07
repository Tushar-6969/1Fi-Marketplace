# 1Fi Marketplace Deep Implementation Plan

This plan focuses on making the marketplace fully functional by implementing interactive search, variant-specific pricing, and the final booking flow.

## Proposed Changes

### [Functional Improvements]

#### [MODIFY] [MarketplaceViewModel.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/ui/MarketplaceViewModel.kt)
- Add `searchQuery` StateFlow.
- Enhance `updateUiState` to filter the product list by both the selected category AND the search query (case-insensitive).
- Implement `onSearchQueryChanged` function.

#### [MODIFY] [MarketplaceModels.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/model/MarketplaceModels.kt)
- **[NEW] `ProductVariant`**: A new class to hold name-specific price/discount info.
- Update `Product` to use `List<ProductVariant>` instead of simple `List<String>`.

#### [MODIFY] [MarketplaceRepository.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/data/MarketplaceRepository.kt)
- Update mock products to include variant-specific data (e.g., iPhone 128GB vs 256GB with different prices).

### [UI Enhancements]

#### [MODIFY] [MarketplaceComponents.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/ui/components/MarketplaceComponents.kt)
- **[REFINE] `SearchBar`**: Make it a controlled component that accepts `value` and `onValueChange`.
- **[NEW] `SuccessAnimationView`**: A screen shown after clicking "Proceed" to simulate a successful booking/application.

#### [MODIFY] [ShopScreen.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/ui/screens/ShopScreen.kt)
- Connect the `SearchBar` to the ViewModel's `searchQuery`.

#### [MODIFY] [MarketplaceDetailsScreen.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/ui/screens/MarketplaceDetailsScreen.kt)
- Update the UI to reflect changes in price/EMI when a different variant is selected.
- Add an `isSuccess` state to show the `SuccessAnimationView` upon completion.

### [Integration]

#### [MODIFY] [MainActivity.kt](file:///C:/Users/admin/AndroidStudioProjects/fimarket_place/app/src/main/java/com/example/fimarket_place/MainActivity.kt)
- Add a new destination for the "Success" screen or handle it as a sub-state of the details flow.

## Verification Plan

### Manual Verification
- **Search Test**: Type "iPh" in the search bar and verify only iPhones are shown.
- **Variant Price Test**: Select "256GB" on the iPhone details screen and verify the price and EMI amounts increase.
- **Success Flow**: Click "Proceed" and verify the success screen appears with the 1Fi-style confirmation.
- **Empty Search**: Type a query that matches nothing and verify a "No products found" state.
