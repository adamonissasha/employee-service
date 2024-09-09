# Employee Service

## Overview

**employee-service** is a microservice designed to manage employee records. It offers RESTful APIs to create, retrieve,
and search for employee information. The service is built using **Spring Boot** and is containerized with **Docker** for
easy deployment.

## Technologies

- **Java 17**
- **Spring Boot 3.3.3**
- **MapStruct**
- **Hibernate Validator**
- **Jackson**
- **Docker**
- **Gradle**

## Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 17**: [Download and install Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Docker**: [Download and install Docker](https://www.docker.com/products/docker-desktop)

### Building the Project

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd employee-service

2. **Build the project using Gradle**:
   ```./gradlew build```

## Running the Application

### Using Docker

1. **Build the Docker image**:
   ```docker build -t employee-service .```

2. **Run the Docker container**:
   ```docker run -p 8080:8080 employee-service```

The application will be accessible at http://localhost:8080.

### Without Docker

1. **Run the JAR file**:
   ```java -jar build/libs/employee-service-0.0.1-SNAPSHOT.jar```

The application will be accessible at http://localhost:8080.

## Configuration
There are no special configuration requirements for this microservice. Ensure that the necessary environment variables are set if required.

## Testing
To run the tests, use the following command:
    ```./gradlew test```

## Postman Collection

You can find the Postman collection for this API [here](https://www.postman.com/orange-firefly-946429/workspace/employeeserviceworkspace/collection/27140416-6bb539f6-ba3f-49ec-9a21-c5067f70871d?action=share&creator=27140416).

## API Endpoints

### Create Employee

- **Endpoint**: `POST /employees`
- **Description**: Creates a new employee.
- **Request Body**:
  ```json
  {
    "firstName": "Mohamed",
    "lastName": "Ahmed",
    "dateOfBirth": "2000-01-01",
    "salary": 1000,
    "joinDate": "2023-05-18",
    "department": "IT"
  }
- **ResponseBody**:
  ```json
  {
    "id": 1
  }

### Get Employee by ID

- **Endpoint**: `GET /employees/{id}`
- **Description**: Retrieves details of an employee by their ID.
- **ResponseBody**:
  ```json
  {
    "id": 1,
    "firstName": "Mohamed",
    "lastName": "Ahmed",
    "dateOfBirth": "2000-01-01",
    "salary": 1000,
    "joinDate": "2023-05-18",
    "department": "IT"
  }

### Search Employees

- **Endpoint**: `GET /employees`
- **Description**: Searches for employees based on the provided parameters.
- **Query Parameters**:
    - **name** (optional): The name or surname of the employee to search for.
    - **fromSalary** (optional): The minimum salary for filtering.
    - **toSalary** (optional): The maximum salary for filtering.
- **ResponseBody**:
  ```json
  [
    {
      "id": 1,
      "firstName": "Mohamed",
      "lastName": "Ahmed",
      "dateOfBirth": "2000-01-01",
      "salary": 1000,
      "joinDate": "2023-05-18",
      "department": "IT"
    },
    {
      "id": 2,
      "firstName": "Salman",
      "lastName": "Ebrahim",
      "dateOfBirth": "1990-01-01",
      "salary": 2000,
      "joinDate": "2016-03-18",
      "department": "Business"
    }
  ]
  
## Error Handling
Common error responses include:
- **400 Bad Request**: Returned for invalid request data.
- **404 Not Found**: Returned when the requested employee is not found.
- **500 Internal Server Error**: Returned for server-side issues.