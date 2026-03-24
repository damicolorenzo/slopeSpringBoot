# slopeSpringBoot

A Spring Boot implementation replicating the Slope project functionality

## Overview

This project is a Spring Boot-based replication of the Slope project, designed to provide a robust and scalable Java application using modern Spring Framework technologies.

## Features

- **Spring Boot Framework**: Build on Spring Boot for rapid application development
- **RESTful Architecture**: Clean and maintainable REST API structure
- **Java-based**: 100% Java implementation
- **Modular Design**: Well-organized project structure for easy maintenance

## Technologies

- **Java**: Primary programming language
- **Spring Boot**: Application framework
- **Maven/Gradle**: Build and dependency management
- **Spring MVC**: Web layer implementation
- **Spring Data JPA**: Database access layer (if applicable)

## Project Structure

```
slopeSpringBoot/
├── .idea/              # IntelliJ IDEA configuration files
├── slope/              # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java source files
│   │   │   └── resources/  # Application resources
│   │   └── test/           # Test files
│   └── pom.xml/build.gradle # Build configuration
└── README.md
```

## Prerequisites

Before running this project, ensure you have the following installed:

- **Java JDK** 11 or higher
- **Maven** 3,6+ or **Gradle** 7.0+ (depending on build tool)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## Getting Started

### Installation

1. **Clone the repository**
  ```bash
  git clone https://github.com/damicolorenzo/slopeSpringBoot.git
  cd slopeSpringBoot
  ```

2. **Navigate to the project directory**
   ```bash
   cd slope
   ```

3. **Build the project**

   Using Maven:
   ```bash
   ./mvnw clean install
   ```

   Using Gradle:
   ```bash
   ./gradlew build
   ```

### Running the Application 

**Using Maven:**
```bash
./mvnw spring-boot:run
```

**Using Gradle**
```bash
./gradlew bootRun
```

**Using Java:**
```bash
java -jar target/slope-0.0.1-SNAPSHOT.jar
```

The application will start and be accessible at 'http://localhost:8080' (default port)

## Author

**Lorenzo D'Amico**
- GitHub: [@damicolorenzo](https://github.com/damicolorenzo)

*Last Updated: March 2026*

