To build and run the project, follow these steps: 

Clone the repository: git clone https://github.com/rahafduglass/juniverse-backend.git

Navigate to the project directory: cd juniverse-backend

Add database "juniverse" to postgres

Build the project: mvn clean install

Run the project: mvn spring-boot:run

register users from src/main/resources/juniverse-mock-users.json using the /api/v1/auth/register-list-of-users 

Access swagger UI at http://localhost:8080/swagger-ui/index.html#/ for API documentaion

-> The application will be available at http://localhost:8080.
