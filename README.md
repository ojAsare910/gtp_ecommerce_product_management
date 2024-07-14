# E-Commerce Product Management System

## Overview

This E-Commerce Product Management System is a full-stack application with a Spring Boot backend and Angular frontend. It provides a robust platform for managing products and categories in an e-commerce environment. The system features a binary tree implementation for efficient category management, RESTful API endpoints for CRUD operations, and integration with PostgreSQL for data persistence.

Key Features:
- Product and Category management
- Binary tree implementation for category organization
- RESTful API endpoints
- PostgreSQL database integration
- Angular frontend for intuitive user interaction
- Spring Boot Actuator for application monitoring and management
- Logging interceptor for request/response logging

## Setup Instructions

### Backend (Spring Boot)

1. Prerequisites:
    - Java 21
    - Maven
    - PostgreSQL

2. Clone the repository:
   - git clone https://github.com/ojAsare910/gtp-product_management-fullstack
     cd gtp-product_management-fullstack

3. Configure the database:
   - Create a PostgreSQL database named `postgres` with schema `pmgmt_db`
   - Update `src/main/resources/application-dev.yml` with your database credentials

4. Build and run the backend:
   - mvn spring-boot:run

The backend will start on `http://localhost:8080`.

### Frontend (Angular)

1. Prerequisites:
   - Node.js and npm

2. Navigate to the frontend directory:
   - cd productmgmt-frontend

3. Install dependencies:
   - npm install

4. Run the Angular development server:
   - ng serve

The frontend will be available at `http://localhost:4200`.

## Basic Usage Guide

1. Access the application:
   Open a web browser and go to `http://localhost:4200`

2. Main Features:
- Products:
   - View product list
   - Add new products
   - Edit existing products
   - Delete products
- Categories:
   - View category list
   - Add new categories
   - Edit existing categories
   - Delete categories

3. RESTful API Endpoints:
- Products:
   - GET `/api/products`: List all products
   - POST `/api/products`: Create a new product
   - GET `/api/products/{id}`: Get a specific product
   - PUT `/api/products/{id}`: Update a product
   - DELETE `/api/products/{id}`: Delete a product

- Categories:
   - GET `/api/categories`: List all categories
   - POST `/api/categories`: Create a new category
   - GET `/api/categories/{id}`: Get a specific category
   - PUT `/api/categories/{id}`: Update a category
   - DELETE `/api/categories/{id}`: Delete a category

## Spring Boot Actuator

Spring Boot Actuator provides production-ready features to help you monitor and manage your application. To access Actuator endpoints:

1. Health check:
   - GET /actuator/health
   
2. Application info:
   - GET /actuator/info

## Demonstration
   - link `https://www.loom.com/share/6725ab65d0224aa1a6a1e78d4bc32224`