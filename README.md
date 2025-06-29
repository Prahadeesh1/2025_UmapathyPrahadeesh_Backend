# 2025_UmapathyPrahadeesh# Coin Change Calculator API

A REST API built with Dropwizard that calculates the minimum number of coins needed to make change for a given amount using dynamic programming algorithms.

## Features

- Calculate minimum coins needed for any amount up to $10,000
- Support for custom coin denominations
- Validates against standard coin denominations (1¢, 5¢, 10¢, 20¢, 50¢, $1, $2, $5, $10, $50, $100, $1000)
- CORS enabled for web frontend integration
- Comprehensive error handling and validation
- Health check endpoint
- Built-in testing suite

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Quick Start

### 1. Build the Application

```bash
mvn clean install
```

### 2. Run the Application

```bash
java -jar target/coin-change-api-1.0-SNAPSHOT.jar server config.yml
```

### 3. Verify It's Running

- Application: http://localhost:8080
- Health Check: http://localhost:8081/healthcheck
- API Health: http://localhost:8080/api/v1/coin-change/health

## API Endpoints

### Calculate Minimum Coins

**POST** `/api/v1/coin-change/calculate`

Calculate the minimum number of coins needed to make change for a given amount.

#### Request Body

```json
{
  "amount": 0.41,
  "denominations": [0.01, 0.05, 0.10, 0.25]
}
```

#### Response (Success)

```json
{
  "coins": [
    {
      "denomination": 0.01,
      "count": 1
    },
    {
      "denomination": 0.05,
      "count": 0
    },
    {
      "denomination": 0.10,
      "count": 1
    },
    {
      "denomination": 0.25,
      "count": 1
    }
  ],
  "totalCoins": 3
}
```

#### Response (Error)

```json
{
  "error": "CALCULATION_ERROR",
  "message": "Cannot make the target amount with given denominations"
}
```

### Get Valid Denominations

**GET** `/api/v1/coin-change/valid-denominations`

Returns a list of all valid coin denominations supported by the API.

#### Response

```json
{
  "validDenominations": [0.01, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 50.00, 100.00, 1000.00]
}
```

### Health Check

**GET** `/api/v1/coin-change/health`

Check if the API service is running properly.

#### Response

```json
{
  "status": "healthy",
  "service": "coin-change-calculator"
}
```

## Request Validation

The API validates all incoming requests:

- **Amount**: Must be between 0 and 10,000 (inclusive)
- **Denominations**: Must contain at least one valid denomination
- **Valid Denominations**: 1¢, 5¢, 10¢, 20¢, 50¢, $1, $2, $5, $10, $50, $100, $1000

## Examples

### Example 1: Standard US Coins

```bash
curl -X POST http://localhost:8080/api/v1/coin-change/calculate \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 0.67,
    "denominations": [0.01, 0.05, 0.10, 0.25]
  }'
```

### Example 2: European Coins

```bash
curl -X POST http://localhost:8080/api/v1/coin-change/calculate \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1.87,
    "denominations": [0.01, 0.02, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00]
  }'
```

### Example 3: Get Valid Denominations

```bash
curl -X GET http://localhost:8080/api/v1/coin-change/valid-denominations
```

## Algorithm

The API uses **Dynamic Programming** to find the optimal solution:

1. Converts dollar amounts to cents for precise integer calculations
2. Uses bottom-up DP approach to find minimum coins needed
3. Reconstructs the solution to show which coins were used
4. Converts results back to dollar denominations

**Time Complexity**: O(amount × number of denominations)  
**Space Complexity**: O(amount)

## Error Handling

The API handles various error scenarios:

- **Invalid Amount**: Outside the range of 0-10,000
- **Invalid Denominations**: Coins not in the valid denomination list
- **Impossible Combinations**: When the target amount cannot be made with given denominations
- **Validation Errors**: Missing required fields or invalid data types

## Development

### Running Tests

```bash
mvn test
```

### Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── CoinChangeApplication.java      # Main application class
│   │   ├── CoinChangeConfiguration.java    # Configuration
│   │   ├── api/                           # DTOs and request/response models
│   │   ├── core/                          # Business logic and services
│   │   └── resources/                     # REST endpoints
│   └── resources/
│       ├── banner.txt                     # Application banner
│       └── config.yml                     # Application configuration
└── test/
    └── java/org/example/
        └── resources/                     # Unit tests
```

### Configuration

The application configuration is in `config.yml`:

```yaml
server:
  applicationConnectors:
    - type: http
      port: 8080
  adminConnectors:
    - type: http
      port: 8081

logging:
  level: INFO
  loggers:
    org.example: DEBUG
```

## CORS Support

CORS is enabled by default to allow web frontend integration. The following headers are supported:

- **Origins**: `*` (all origins)
- **Methods**: `GET, POST, PUT, DELETE, HEAD, OPTIONS`
- **Headers**: `X-Requested-With, Content-Type, Accept, Origin, Authorization`

## License

This project is available under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Support

For questions or issues, please open an issue in the repository.