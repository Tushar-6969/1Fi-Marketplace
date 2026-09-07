# 1Fi Marketplace – Android Assignment

## Overview
This project implements the **1Fi Marketplace** experience as part of the SDE Intern assignment. The marketplace is integrated into the existing "Shop" flow, providing a seamless user journey from discovery to application submission.

**User Journey:**
Shop → 1Fi Marketplace → Product Listing → Product Details → Variant Selection → EMI Plan Selection → Proceed

## Assignment Requirements
The project satisfies the core requirements of the assignment:
- **Product Discovery**: Comprehensive listing with search and category filtering.
- **Visual Reference**: High-fidelity product details including descriptions and multi-variant support.
- **Selection Logic**: Dynamic pricing and EMI updates based on product variants.
- **EMI Integration**: Presentation and selection of multiple EMI plans from a structured model.
- **Functional Flow**: A functional primary CTA that captures user selections.
- **UI/UX Fidelity**: Design language consistent with the 1Fi app (purple brand color, rounded cards, specific layout spacing).
- **Data Integrity**: Decoupled architecture using structured models instead of hardcoding UI data.
- **Error Resilience**: Explicit handling of Loading, Success, and Error UI states.

*Note: The "Top Brands" and "Nearby Stores" tabs are intentionally designed with placeholder empty states as per the assignment scope.*

## Features Implemented
- **Marketplace Hub**: Full-featured product listing with promotional carousel and category tabs.
- **Active Search**: Real-time filtering of the product list via a controlled search component.
- **Product Cards**: Clean UI featuring base pricing and EMI availability indicators.
- **Product Details Screen**: Deep-dive view for specific products with full highlights.
- **Variant Selection**: Interactive choice of variants with real-time price and EMI recalibration.
- **EMI Selection Engine**: Selectable plans with per-month installment calculations driven by the data layer.
- **Success Flow**: Visual confirmation screen simulating a successful application submission.
- **Loading & Error States**: Standardized views for data fetching and error handling with retry actions.
- **Modern Navigation**: Bottom navigation for app levels and stack navigation for feature flows.
- **Mock Data Layer**: A repository-patterned data source ready for future API integration.

## Technical Implementation
The application follows a clean, reactive architecture:
- **Kotlin**: Core programming language.
- **Jetpack Compose**: Modern declarative UI toolkit using Material 3.
- **ViewModel**: Manages UI-related data and business logic.
- **StateFlow**: Reactive state management for real-time UI updates.
- **Repository Pattern**: Abstracts data source logic from the presentation layer.
- **Navigation Compose**: Handles all screen transitions and argument passing.

**Data Flow:**
`Repository (Mock)` → `ViewModel` → `UiState` → `Composable UI`

The mock data is separated into a repository implementation, ensuring that the UI remains agnostic to the data source. This allows for a seamless swap to a real REST API in the future.

## Project Structure
```text
app/src/main/java/com/example/fimarket_place/
├── data/
│   └── MarketplaceRepository.kt       # Mock data source & logic
├── model/
│   └── MarketplaceModels.kt           # Data classes (Product, Variant, EmiPlan)
├── ui/
│   ├── components/
│   │   └── MarketplaceComponents.kt   # Reusable UI (Carousel, SearchBar, Success UI)
│   ├── screens/
│   │   ├── ShopScreen.kt              # Shop container with tabs
│   │   └── MarketplaceDetailsScreen.kt # Product details & EMI flow
│   ├── MarketplaceViewModel.kt        # State management & filtering logic
│   └── theme/                         # Material 3 theming & 1Fi brand colors
└── MainActivity.kt                    # Entry point & Navigation Graph
```

## UI/UX
The implementation focuses on maintaining the aesthetic and functional standards of the 1Fi app:
- **Brand Consistency**: Uses the signature purple brand color (#6200EE) and typography.
- **Consistent Layouts**: Card corner radius (20dp) and spacing match the provided screenshots.
- **Clear Hierarchy**: High-contrast prices, bold headings, and accessible buttons.
- **Responsive Elements**: Lists use `LazyColumn` and `LazyRow` for optimal performance.

## Data & API Approach
While the assignment uses mock data, the project implements a **Repository Interface**. This decoupling means the `MarketplaceViewModel` is not tied to the specific implementation details of the data source.
- **Latency Simulation**: The repository includes artificial delays to demonstrate loading states.
- **Structured Models**: Variants and EMI plans are nested objects, supporting complex real-world pricing scenarios.

## States & Error Handling
The application manages distinct states for a professional user experience:
- **Loading**: Circular progress indicator during data retrieval.
- **Success**: Populates the UI with products, offers, and categories.
- **Error**: Stylized error message with a "Retry" button.
- **Search Empty**: Specific feedback when no products match a user's query.

## How to Run
1. **Clone** the repository to your local machine.
2. **Open** the project in Android Studio.
3. Allow **Gradle sync** to finish.
4. **Run** on an Android emulator or physical device.
   - **Minimum SDK**: 24 (Android 7.0)
   - **Target SDK**: 37
5. Open the **Shop** tab and navigate to the **1Fi Marketplace** section.

## Testing / Verification
- **Automated Tests**: `MarketplaceViewModelTest.kt` contains unit tests verifying state transitions from `Loading` to `Success` and correct data propagation.
- **Manual Verification**:
    - Real-time search and category filtering logic.
    - Dynamic price/EMI updates when switching variants.
    - End-to-end flow from browsing to success screen.

## Engineering Decisions
- **Reusability**: UI components are designed to be stateless where possible to increase reusability.
- **MVVM Pattern**: Strictly followed to ensure a clean separation of concerns.
- **Single Source of Truth**: The ViewModel acts as the mediator between the repository and the UI.
- **Material 3**: Leveraged for modern design standards and accessibility.

## Assignment Scope
This implementation is focused specifically on the **1Fi Marketplace** requirements. Unrelated screens (Home, Profile, etc.) are maintained as placeholders to preserve the 1Fi navigation structure without exceeding the assignment's functional scope.

## Future Improvements
- **Real Backend Integration**: Migration to a Retrofit/Ktor-based API.
- **Remote Image Loading**: Integration of Coil for efficient network-based imagery.
- **Local Persistence**: Implementation of Room for offline support.
- **Real EMI Eligibility**: Integration with a credit assessment API.

---
*Submitted for the 1Fi SDE Intern Assignment.*
