#!/bin/bash
# Bash script to build and run the IoT Data Processing app locally

# Exit on error
set -e

# Build the project
mvn clean install

# Start the Spring Boot application
mvn spring-boot:run
