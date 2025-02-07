# Digimon Spring Boot Application

This project is a Spring Boot application centered around the Digimon universe.

It demonstrates the use of Testcontainers for integration testing, along with JUnit 5 and the JUnit Jupiter API. The application is containerized using Docker, with services and
the database orchestrated via Docker Compose.

The codebase achieves 100% test coverage across Controllers, Services, Repositories, Mappers, Exception handling, Utility and Constant classes.

## Technologies Used

- `Java 21`
- `Maven 3.9.9`
- `Spring Boot 3.4.2`
- `Docker`
- `MapStruct 1.6.3`
- `JUnit 5`
- `Testcontainers 1.20.4`

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21**.
- **Maven 3.9.9**.
- **Docker**.

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/angelgamaza/digimon-testing.git
   
   cd digimon-testing
   ```

2. **Build the Application:**

    ```bash
    mvn clean install
   ```

## Running the Application

The application can be run using Docker Compose, that ensures that both the application and its dependencies (like the database) are containerized and managed together.

1. **Start Services:**

    ```bash
   docker-compose up --build
   ```

2. **Access the Application:** Once the services are up, navigate to <http://localhost:8081> to interact with the application.

## Running Tests

The project includes comprehensive tests using JUnit 5 and Testcontainers.

1. **Ensure Docker is Running:** Testcontainers requires Docker to run integration tests.

2. **Execute Tests:**

    ```bash
    mvn test
    ```

## Author

Ángel Gamaza
