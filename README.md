# KursMobileApp
Coursework on the topic: Custom mobile application - cinema ticket sales office
This repository contains the source code and project documentation for a mobile Android application developed as part of a university course project. The application allows users to view a catalog of movies, read detailed descriptions, select a screening, choose seats, and complete a ticket purchase — all from within a convenient and user-friendly interface.
## Project Overview

The mobile application is designed for Android devices and built using **Java** in **Android Studio**. It follows the **Single Activity – Multiple Fragments** architecture to ensure modularity, scalability, and ease of maintenance.

Key features of the application include:
- User registration and login (with session storage)
- Movie list with posters, overviews, and release dates
- Detailed movie information fetched via an external REST API
- Seat selection and booking interface
- Firebase integration for user management
- Smooth navigation between fragments using `NavController`
- Responsive design using `ConstraintLayout`, `RecyclerView`, and XML resources

## App Architecture

The project follows a modular package structure:

- `activities/` – Contains the main activity (`MainActivity`) that loads the first fragment (`LoginFragment`) and hosts all navigation.
- `fragments/` – Includes UI fragments: `LoginFragment`, `RegisterFragment`, `MovieListFragment`, `FragmentMovieDetail`, `BookingFragment`, and `PurchaseFragment`.
- `models/` – Holds model classes: `Movie`, `MovieDetail`, and `MovieResponse`.
- `network/` – Defines Retrofit client and API interface (`ApiClient`, `MovieApi`) for fetching movie data.
- `adapters/` – Contains `MovieAdapter`, used for displaying movie items in a list.
- `res/` – Follows Android standard resource structure:
  - `layout/` – XML layouts for UI components
  - `drawable/` – Images and icons
  - `mipmap/` – App launcher icons
  - `values/` – Colors, strings, styles
  - `xml/` – System-level configurations (`backup_rules.xml`, `data_extraction_rules.xml`)

## Technologies Used

- **Java** as the main programming language  
- **Android SDK & Android Studio**  
- **Firebase Firestore** – for user data storage and verification  
- **Retrofit** – to communicate with the TMDB movie database  
- **Glide** – to load and cache poster images  
- **SharedPreferences** – for local session management  
- **Room (planned)** – optional local data storage

## Testing

The app was tested using the **black-box testing method** by giving the build to several users. The tests validated:
- User registration and login flows
- Movie list loading with different internet speeds
- Navigation stability between fragments
- Booking and purchase scenarios

## Course Contribution

This project served as a hands-on opportunity to consolidate skills in:
- Android UI/UX design
- Integration with external APIs (TMDB)
- Firebase backend setup
- Application architecture planning
- Mobile-specific data handling (local and remote)

This course project is intended for **educational use only**. Code and architecture can be reused or extended with attribution.

