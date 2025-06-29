# Coin Change Calculator API

A high-performance REST API built with Dropwizard that calculates the minimum number of coins needed to make change for a given amount using dynamic programming algorithms.

## 🚀 Features

- **Optimal Algorithm**: Uses dynamic programming to find the minimum number of coins
- **Flexible Denominations**: Support for custom coin denominations up to $1000
- **Robust Validation**: Comprehensive input validation and error handling
- **CORS Enabled**: Ready for web frontend integration
- **Health Monitoring**: Built-in health check endpoints
- **Docker Support**: Containerized deployment ready
- **High Performance**: Efficient algorithms with O(amount × denominations) complexity
- **Comprehensive Testing**: Full test suite included

## 📋 Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher
- **Docker** (optional, for containerized deployment)

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── CoinChangeApplication.java         # Main application entry point
│   │   ├── CoinChangeConfiguration.java       # Application configuration
│   │   ├── api/                              # DTOs and API models
│   │   │   ├── CCRequest.java                # Request model
│   │   │   ├── CCResponse.java               # Success response model
│   │   │   ├── CInfo.java                    # Coin information model
│   │   │   └── ErrorResponse.java            # Error response model
│   │   ├── core/                             # Business logic
│   │   │   └── CCService.java                # Coin change calculation service
│   │   └── resources/                        # REST endpoints
│   │       └── CoinChangeResource.java       # API resource endpoints
│   └── resources/
│       ├── banner.txt                        # Application startup banner
│       └── config.yml                        # Application configuration
└── test/
    └── java/org/example/resources/
        └── CoinChangeResourceTest.java        # Unit tests
```

## 🚀 Quick Start

### 1. Clone and Build

```bash
git clone <repository-url>
cd coin-change-api
mvn clean install
```

### 2. Run the Application

```bash
java -jar target/coin-change-api-1.0-SNAPSHOT.jar server config.yml
```

### 3. Verify Setup

- **Application Endpoint**: http://localhost:8080
- **Admin/Health Check**: http://localhost:8081/healthcheck
- **API Health Check**: http://localhost:8080/api/v1/coin-change/health

## 🐳 Docker Deployment

### Build Docker Image

```bash
# First build the JAR file
mvn clean package

# Build Docker image
docker build -t coin-change-api .
```

### Run Container

```bash
docker run -p 8080:8080 -p 8081:8081 coin-change-api
```

## 📖 API Documentation

### Calculate Minimum Coins

**POST** `/api/v1/coin-change/calculate`

Calculates the minimum number of coins needed to make change for a given amount.

#### Request Body

```json
{
  "amount": 0.67,
  "denominations": [0.01, 0.05, 0.10, 0.25]
}
```

#### Success Response (200 OK)

```json
{
  "coins": [
    {
      "denomination": 0.01,
      "count": 2
    },
    {
      "denomination": 0.05,
      "count": 1
    },
    {
      "denomination": 0.10,
      "count": 1
    },
    {
      "denomination": 0.25,
      "count": 2
    }
  ],
  "totalCoins": 6
}
```

#### Error Response (400 Bad Request)

```json
{
  "error": "CALCULATION_ERROR",
  "message": "Cannot make the target amount with given denominations"
}
```

### Get Valid Denominations

**GET** `/api/v1/coin-change/valid-denominations`

Returns all supported coin denominations.

#### Response

```json
{
  "validDenominations": [0.01, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 50.00, 100.00, 1000.00]
}
```

### Health Check

**GET** `/api/v1/coin-change/health`

API service health status check.

#### Response

```json
{
  "status": "healthy",
  "service": "coin-change-calculator"
}
```

## 🔧 Configuration

The application uses `config.yml` for configuration:

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
  appenders:
    - type: console
      threshold: INFO
      timeZone: UTC
      target: stdout
```

## 🛡️ Validation Rules

### Amount Validation
- **Range**: 0 to 10,000 (inclusive)
- **Precision**: Up to 2 decimal places (cents)

### Denomination Validation
- **Supported Values**: 1¢, 5¢, 10¢, 20¢, 50¢, $1, $2, $5, $10, $50, $100, $1000
- **Requirement**: At least one denomination must be provided
- **Format**: Decimal values (e.g., 0.25 for 25 cents)

## 💡 Usage Examples

### Example 1: US Standard Coins

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
    "amount": 2.47,
    "denominations": [0.01, 0.02, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00]
  }'
```

### Example 3: Large Denominations

```bash
curl -X POST http://localhost:8080/api/v1/coin-change/calculate \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 153.00,
    "denominations": [1.00, 5.00, 10.00, 50.00, 100.00]
  }'
```

### Example 4: Check Valid Denominations

```bash
curl -X GET http://localhost:8080/api/v1/coin-change/valid-denominations
```

## 🧮 Algorithm Details

The API implements an optimized **Dynamic Programming** solution:

### Algorithm Steps:
1. **Input Validation**: Validates amount range and denomination validity
2. **Precision Handling**: Converts dollars to cents for exact integer arithmetic
3. **DP Table Construction**: Builds optimal solution table bottom-up
4. **Solution Reconstruction**: Traces back to find actual coins used
5. **Result Formatting**: Converts back to dollar denominations

### Complexity Analysis:
- **Time Complexity**: O(amount × number of denominations)
- **Space Complexity**: O(amount)

### Key Features:
- Handles edge cases (amount = 0, impossible combinations)
- Precision-safe arithmetic using integer cents
- Optimal coin selection guaranteed

## 🧪 Testing

### Run All Tests

```bash
mvn test
```


### Example Test Cases:
- Valid coin calculations
- Invalid denomination handling
- Amount boundary validation
- Health check verification

## 🚨 Error Handling

The API provides comprehensive error handling:

### Error Types:

1. **CALCULATION_ERROR**: Cannot make target amount with given denominations
2. **VALIDATION_ERROR**: Invalid input parameters
3. **INTERNAL_ERROR**: Unexpected server errors

### Error Response Format:

```json
{
  "error": "ERROR_TYPE",
  "message": "Human-readable error description"
}
```

## 🌐 CORS Configuration

CORS is pre-configured for web frontend integration:

- **Allowed Origins**: `*` (all origins)
- **Allowed Methods**: `GET, POST, PUT, DELETE, HEAD, OPTIONS`
- **Allowed Headers**: `X-Requested-With, Content-Type, Accept, Origin, Authorization`
- **Credentials**: Supported

## 🔍 Monitoring & Health Checks

### Application Health

```bash
# Dropwizard admin health check
curl http://localhost:8081/healthcheck

# Custom API health check
curl http://localhost:8080/api/v1/coin-change/health
```

### Metrics

Access Dropwizard metrics at:
- **Metrics**: http://localhost:8081/metrics
- **Health**: http://localhost:8081/healthcheck

## 🛠️ Development

### Development Setup

```bash
# Clone repository
git clone <repository-url>
cd coin-change-api

# Install dependencies
mvn clean install

# Run in development mode
mvn exec:java -Dexec.mainClass="org.example.CoinChangeApplication" -Dexec.args="server config.yml"
```


**Built with using Dropwizard, Java, and Dynamic Programming**