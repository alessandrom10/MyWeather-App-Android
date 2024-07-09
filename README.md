# MyWeather: Project for Android

MyWeather is a simple application that displays weather information for some specified locations. To get started, you'll need to set up your own API key.

## Getting Started

### Prerequisites

- Android Studio installed on your machine
- A Weather API key from OpenWeatherMap

### Installation
- Clone the repository
- Write into the local.properties file a property called "weatherKey" with your personal key for the OpenWeatherMap api

# Description
**MyWeather** is an Android application developed in Java.
Below are the key points and known issues of the application:

## Application Functionality
Despite the geocode issue, the application meets all the required specifications and is fully functional and executable.

### Architecture
The application follows the Model-View-Controller (MVC) architectural pattern:
- **Model:** `LocalData` class provides data to the rest of the application.
- **View Controllers:** Various activities manage information for the views.

### Navigation
The app is organized hierarchically:
- Each new view (except the map, to ensure a full-screen image) can return to the main screen (root view) by pressing the back button in the action bar.

### Asynchronous Operations
All potentially long-blocking calls are made asynchronous to ensure an interactive user experience, even while loading weather forecasts or creating custom markers on the map.

### Efficient Resource Use
All data lists are displayed using `RecyclerView` and the respective adapter to ensure efficient use of smartphone resources.

## Geocode Problem

### Issue
The geocode functionality is non-operational, resulting in various errors and causing the app to crash. The most frequent error encountered is "deadline exceeded."

### Attempts to Resolve
Multiple solutions were attempted to fix the geocode issue, including:
- Running geocode in a separate thread/task
- Using a personalized API key
- Accessing geocode through `HttpClient`

None of these attempts were successful.

### Workaround
An attempt to use `HttpClient` to access the JSON data directly also resulted in a timeout exception. As a result, the geocode code and the `HttpClient` call code are present in the program but commented out.

## Application Modifications
To mitigate the geocode problem, the application has been modified to:
- Simulate user localization, always returning the position of Parma.
- Return Palermo as the default city when another city is requested.
- Pre-insert four favorite cities to demonstrate the map functionalities without relying on geocode.

## Conclusion
MyWeather is a robust application that, aside from the geocode issue, meets all requirements and provides a seamless user experience.

---

Feel free to explore the code!
