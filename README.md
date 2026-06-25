🏋️ Fitness Tracker Microservice Platform

A cloud-native fitness tracking platform built using Spring Boot Microservices, React, Spring Cloud, Kafka, Keycloak, and Google Gemini AI.

The application enables users to securely track workouts and activities while leveraging AI-powered insights for personalized fitness recommendations and analysis.

📖 Overview

This project demonstrates a production-style microservice architecture where multiple independent services collaborate through API Gateway, Service Discovery, Event Streaming, and AI integrations.

Users can:

Register and authenticate securely
Log fitness activities
Track workout history
Manage personal profiles
Receive AI-generated fitness insights
Experience scalable and loosely coupled microservices
🏗️ System Architecture

Request Flow
Frontend/Postman sends requests to API Gateway.
Gateway validates access tokens with Keycloak.
Requests are routed to appropriate microservices.
User Service manages user-related operations.
Activity Service handles workout/activity records.
Activity events are published to Kafka.
AI Service consumes activity events from Kafka.
AI Service communicates with Google Gemini API.
AI-generated insights are returned to the platform.
🧩 Architecture Components
API Gateway

Acts as the single entry point for all client requests.

Responsibilities:

Request routing
Authentication enforcement
Security filtering
Centralized access management
Keycloak

Handles authentication and authorization.

Features:

OAuth2 Authentication
JWT Token Generation
Role-Based Access Control (RBAC)
Secure user management
Eureka Server

Provides service discovery.

Benefits:

Dynamic service registration
Load balancing support
Reduced service coupling
Config Server

Centralized configuration management.

Benefits:

Externalized configurations
Environment-specific properties
Easy configuration updates
User Service

Responsible for:

User profile management
User registration
User information retrieval
Activity Service

Responsible for:

Recording workouts
Tracking fitness activities
Publishing activity events to Kafka

Examples:

Running
Walking
Cycling
Gym Workouts
Kafka

Acts as the event streaming platform.

Benefits:

Asynchronous communication
Event-driven architecture
High scalability
Loose coupling between services
AI Service

Processes activity data and generates fitness recommendations.

Responsibilities:

Consuming Kafka events
Preparing prompts
Calling Gemini APIs
Returning AI-generated insights
Google Gemini Integration

Provides:

Workout recommendations
Fitness analysis
Personalized suggestions
Goal-oriented guidance
⚙️ Tech Stack
Backend
Java 21
Spring Boot
Spring Cloud Gateway
Spring Security
Spring Data JPA
Spring Cloud Config
Eureka Discovery Server
Apache Kafka
Keycloak
Hibernate
Frontend
React.js
Material UI (MUI)
Axios
Database
MySQL
AI
Google Gemini API
DevOps
Docker
Docker Compose
Git
GitHub
📂 Microservices Structure
fitness-tracker-microservice
│
├── api-gateway
│
├── user-service
│
├── activity-service
│
├── ai-service
│
├── discovery-server
│
├── config-server
│
├── frontend
│
└── docker-compose.yml
🔐 Security

Authentication is implemented using Keycloak and OAuth2.

Security Features
JWT-based authentication
Role-based authorization
Protected APIs
Secure Gateway validation
Token-based access control
📡 Event-Driven Communication

The platform uses Kafka for asynchronous messaging.

Example Flow
User Creates Activity
        ↓
Activity Service
        ↓
Kafka Topic
        ↓
AI Service
        ↓
Google Gemini
        ↓
Fitness Recommendation

Benefits:

High scalability
Improved reliability
Loose service coupling
Better fault tolerance
🤖 AI Features

Using Google Gemini, the platform can generate:

Workout Recommendations

Example:

Based on your recent running activity, increase your weekly distance by 10% and include one recovery day.

Fitness Insights

Example:

Your activity consistency improved by 18% compared to last month.

Personalized Suggestions

Example:

Consider adding strength training twice a week to complement your cardio routine.

🚀 Running the Project
Clone Repository
git clone https://github.com/kartikbhandare7/fitness-tracker-microservice.git

cd fitness-tracker-microservice
Start Infrastructure
docker-compose up -d

This starts:

Kafka
Zookeeper
MySQL
Keycloak
Supporting services
Start Backend Services

Run services in the following order:

1. Config Server
2. Eureka Server
3. API Gateway
4. User Service
5. Activity Service
6. AI Service
Start Frontend
cd frontend

npm install

npm start
📌 API Examples
Create Activity
POST /activities

Request:

{
  "type": "Running",
  "duration": 45,
  "caloriesBurned": 400
}
Get Activities
GET /activities
Get AI Recommendation
GET /ai/recommendation

Response:

{
  "message": "Increase your running distance gradually and include strength training."
}
🧪 Future Enhancements
Activity analytics dashboard
Real-time notifications
Email integration
Wearable device integration
AI meal planning
Fitness goal tracking
Leaderboards and challenges
Kubernetes deployment
CI/CD pipelines
🎯 Key Concepts Demonstrated
Microservices Architecture
API Gateway Pattern
Service Discovery
Centralized Configuration
Event-Driven Architecture
Kafka Messaging
OAuth2 & JWT Security
RBAC Authorization
AI Integration
Spring Cloud Ecosystem
👨‍💻 Author
Kartik Bhandare

Java Backend Developer

GitHub: Kartik Bhandare GitHub
