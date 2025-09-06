# Weather Forecast Application

This project fetches weather forecasts for a list of cities from [WeatherAPI](https://www.weatherapi.com/) and generates a tabular ASCII weather report.

---

## 👂 Project Structure

```
com.example.weather
│
├── application
│   ├── port
│   │   ├── WeatherForecastProvider.java   # Interface for fetching weather data
│   │   └── WeatherReportWriter.java       # Interface for writing reports
│   └── usecase
│       └── WeatherForecastUseCase.java    # Orchestrates fetching, building, and reporting
│
├── domain
│   ├── annotation
│   │   └── Column.java                    # Annotation for custom column names
│   ├── marker
│   │   └── Reportable.java                # Marker interface for reportable entities
│   ├── model
│   │   ├── WeatherData.java               # Domain model for forecast data
│   │   └── WeatherReportRow.java          # Entity representing a report row
│   └── service
│       ├── WeatherReportBuilder.java      # Builds report rows from forecast data
│       └── WindAnalyzer.java              # Analyzes most common wind direction
│
├── infrastructure
│   ├── api
│   │   ├── WeatherApi.java                # Retrofit API definition
│   │   ├── WeatherApiClient.java          # API client implementation
│   │   ├── dto
│   │   │   └── WeatherForecastResponse.java # DTO mapping API JSON response
│   │   └── exception
│   │       ├── NoForecastAvailableException.java
│   │       ├── WeatherApiException.java
│   │       └── WeatherReportException.java
│   ├── config
│   │   ├── RetrofitFactory.java           # Creates Retrofit instance
│   │   └── WeatherConfig.java             # Loads API key and cities
│   ├── mapper
│   │   └── WeatherForecastMapper.java     # MapStruct mapper: DTO → Domain model
│   └── output
│       └── WeatherReportGenerator.java    # ASCII table report generator
│
└── WeatherApp.java                        # Application entry point
```

---

## 🔹 Requirements

- Java 17+
- Maven
- Internet connection (for WeatherAPI)
- Valid WeatherAPI key

---

## ⚙️ Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/weather-app.git
cd weather-app
```

2. Add your WeatherAPI key to `config.properties`:

```properties
WEATHER_API_KEY=your_api_key_here
cities=Paris,London,Berlin
```

3. Build the project:

```bash
mvn clean install
```

---

## 🏃 Running the Application

Run the main class:

```bash
java -cp target/weather-app-1.0-SNAPSHOT.jar com.example.weather.WeatherApp
```

Output: ASCII table of tomorrow's weather forecasts for configured cities.

---

## 🔹 Example Report

```
+--------+------------+-------------------------+-------------------------+---------------+------------------+----------------+
|  City  |    Date    | Minimum Temperature (°C)| Maximum Temperature (°C)| Humidity (%)  | Wind Speed (kph) | Wind Direction |
+--------+------------+-------------------------+-------------------------+---------------+------------------+----------------+
| Paris  | 2025-09-08 | 12.5                    | 21.3                    | 65.0          | 14.0             | SW             |
| London | 2025-09-08 | 11.0                    | 20.0                    | 60.0          | 12.5             | NW             |
+--------+------------+-------------------------+-------------------------+---------------+------------------+----------------+
```

---

## 📝 Key Concepts

- **Clean Architecture**
  - `application`: Ports & use cases
  - `domain`: Core models & business logic
  - `infrastructure`: API, mappers, report generator

- **Type Safety**
  - `WeatherReportWriter<T extends Reportable>` ensures only reportable entities are used for reports.

- **Extensibility**
  - Add new report formats by implementing `WeatherReportWriter`.
  - Add new API providers by implementing `WeatherForecastProvider`.

---

## ⚡ Dependencies

- Retrofit + Gson (API client)
- MapStruct (DTO → domain mapping)
- Asciitable (`de.vandermeer.asciitable`) for ASCII table generation
- Apache Commons Lang (`FieldUtils`) for reflection

---

