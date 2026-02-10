Project Overview

A multi-user task management system designed to demonstrate real-world backend development using Spring Boot, focusing on security, role-based access control, scalable architecture, and production-ready practices.
A multi-user system where users can:
Current Features

User Features
> Register and log in using JWT-based authentication
> Create personal  task
> Assign tasks to themselves
> View their own tasks

Admin Features
> Assign roles to users (ADMIN / USER)
> Access sensitive admin endpoints (e.g., creating team projects)
> View and manage all projects and tasks
> Add tasks to projects
> create team projects

Planned Enhancements (In Progress)

Security Enhancements
>Implement JWT refresh token mechanism
>Token revocation and secure logout

Task Management Enhancements
>Task lifecycle management (TODO, IN_PROGRESS, BLOCKED, DONE)
>Task priority levels and due dates
>Business rule enforcement for task status transitions

System & Backend Improvements
>Global exception handling with standardized error responses
>Audit logging to track create, update, and delete actions
>Asynchronous processing for non-blocking operations

Testing & Quality Assurance
>Unit testing for service-layer logic
>Integration testing with real database using Testcontainers
>Security and authorization tests

Deployment & DevOps
>Dockerization of the application
>Docker Compose setup for application and database
>Production-ready configuration

 Technology Stack
>Spring Boot (Java)
>Spring Security with JWT
>Hibernate / JPA
>MySQL (Relational Database)
>RESTful APIs
>Docker & Docker Compose(planned)
>JUnit, Mockito, Testcontainers(planned)



