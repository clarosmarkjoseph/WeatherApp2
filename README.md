
# Weather Application

An android Weather Application that uses android MVVM clean architecture using Kotlin


### How to enable the API https://api.openweathermap.org/ to your application

Please check the kotlin object **WeatherMapApiConstants.kt** and insert your **API** key under variable **API_ID**

### Features:

- Tab for current weather
- Tab for weather history fetched
- Unit test

### Technologies Used:

- SOLID Principle (Clean architecture: Domain-Data-Presentation layer)
- Kotlin as programming language
- MVVM Architecure (Model-View-ViewModel)
- Coroutines: for async task
- Retrofit: HTTP Client request


### Jetpack Components:

- Room: a database for storing history of weather.
- Compose pager: for Horizontal pager of tab
- Jetpack compose: a declarative way of writing UI instead of XML.
- Dagger Hilt: a dependency injection library
- Koil: a library for image loader


### Unit test:

- JUnit 4
- Mockk: a library for mocking and stubbing data to your unit test

### Example Screen:

![](Screenshot_20230514_151701.png)
![](Screenshot_20230514_151719.png)