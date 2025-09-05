# WeatherApp

A modular, non-Spring Java application that fetches weather forecasts from [WeatherAPI](https://www.weatherapi.com/), builds domain-level weather reports, and renders them as ASCII tables in the console.  
The design follows a **hexagonal architecture** (ports and adapters), keeping business logic independent from infrastructure.

---

## Features

- Fetches weather forecasts for one or more cities from WeatherAPI.
- Builds structured domain models (`WeatherData`, `WeatherReportRow`) with temperature, humidity, wind speed, and wind direction.
- Determines most common wind direction using `WindAnalyzer`.
- Generates tabular weather reports with headers derived from `@Column` annotations.
- Renders output as ASCII tables in the console.

---

## Project Structure

```
com.example.weather
│
├── WeatherApp                → entry point (manual wiring)
│
├── application
│   ├── port                   → interfaces (ports)
│   │   ├── HeaderExtractor
│   │   ├── RowExtractor
│   │   ├── TableRenderer
│   │   ├── WeatherForecastProvider
│   │   └── WeatherReportWriter
│   │
│   └── usecase
│       └── WeatherForecastUseCase → orchestrates workflow (fetch → build → write)
│
├── domain
│   ├── annotation
│   │   └── Column              → marks fields for table output
│   │
│   ├── model
│   │   ├── WeatherData         → domain forecast data (record)
│   │   ├── WeatherReportRow    → row in final report (record)
│   │
│   └── service
│       ├── WeatherReportBuilder → builds report rows from domain model
│       └── WindAnalyzer         → calculates most common wind direction
│
├── infrastructure
│   ├── api                     → external weather API client
│   │   ├── WeatherApi          → Retrofit interface
│   │   ├── WeatherApiClient    → implements WeatherForecastProvider
│   │   ├── dto                 → WeatherForecastResponse (DTOs for JSON mapping)
│   │   └── exception           → WeatherApiException, NoForecastAvailableException
│   │
│   ├── config
│   │   ├── RetrofitFactory     → builds Retrofit instance
│   │   └── WeatherConfig       → loads config.properties
│   │
│   ├── extractor
│   │   ├── ColumnAnnotationHeaderExtractor → implements HeaderExtractor
│   │   └── ReflectionRowExtractor          → implements RowExtractor
│   │
│   ├── mapper
│   │   └── WeatherForecastMapper → maps API DTO → domain WeatherData (MapStruct)
│   │
│   └── output
│       ├── AsciiTableRenderer   → renders tables to console (implements TableRenderer)
│       └── WeatherReportPrinter → implements WeatherReportWriter (header + rows + renderer)                # Report rendering and printing
```

---

## Flow

1. `WeatherApp.main` wires dependencies.
2. `WeatherForecastUseCase.runForecastForTomorrow` executes:
   - Calls `WeatherForecastProvider` → implemented by `WeatherApiClient` → fetches `WeatherData`.
   - Builds rows with `WeatherReportBuilder` and `WindAnalyzer`.
   - Delegates to `WeatherReportWriter` (`WeatherReportPrinter`) for output.
3. `WeatherReportPrinter`:
   - Extracts headers from `@Column` annotations.
   - Extracts row values via reflection.
   - Passes headers + rows to `TableRenderer` (`AsciiTableRenderer`) → returns ASCII table as String object.

---

## Configuration

The app loads settings from `config.properties` (classpath resource):

```properties
WEATHER_API_KEY=your_api_key_here
cities=London,Berlin,Paris
```

- `WEATHER_API_KEY`: API key from [WeatherAPI](https://www.weatherapi.com/).  
- `cities`: Comma-separated list of city names.  

---

## Run

1. Add your `config.properties` file to `src/main/resources/`.
2. Build the project with Maven:

```bash
mvn clean package
```

3. Run the JAR:

```bash
java -jar target/weatherapp-1.0-SNAPSHOT.jar
```

---

## Example Output

```
┌───────────┬────────────┬──────────────────────────┬──────────────────────────┬──────────────┬──────────────────┬────────────────┐
│   City    │    Date    │ Minimum Temperature (°C) │ Maximum Temperature (°C) │ Humidity (%) │ Wind Speed (kph) │ Wind Direction │
├───────────┼────────────┼──────────────────────────┼──────────────────────────┼──────────────┼──────────────────┼────────────────┤
│ Chisinau  │ 2025-09-06 │           14.5           │           30.9           │     30.0     │       30.0       │       NE       │
├───────────┼────────────┼──────────────────────────┼──────────────────────────┼──────────────┼──────────────────┼────────────────┤
│  Madrid   │ 2025-09-06 │           18.8           │           34.0           │     45.0     │       45.0       │       E        │
├───────────┼────────────┼──────────────────────────┼──────────────────────────┼──────────────┼──────────────────┼────────────────┤
│   Kyiv    │ 2025-09-06 │           16.3           │           27.5           │     41.0     │       41.0       │       E        │
├───────────┼────────────┼──────────────────────────┼──────────────────────────┼──────────────┼──────────────────┼────────────────┤
│ Amsterdam │ 2025-09-06 │           11.6           │           23.3           │     62.0     │       62.0       │      SSE       │
└───────────┴────────────┴──────────────────────────┴──────────────────────────┴──────────────┴──────────────────┴────────────────┘
```

---

## Technologies Used

- **Java 17+**
- **Retrofit2** (HTTP client)
- **Gson** (JSON deserialization)
- **MapStruct** (DTO → domain mapping)
- **Apache Commons Lang** (reflection utilities)
- **AsciiTable** (console table rendering)
- **Lombok** (builders, annotations)

---
