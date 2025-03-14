# SchedulerX - Job Scheduler

SchedulerX is a job scheduling application that periodically fetches schedules from a database and executes registered commands based on the parameters.

## Getting Started

### 1. Clone the Repository

```sh
git clone https://github.com/v-s-codes/schedulerx.git
cd schedulerx
```

### 2. Set Up Database

Create a PostgreSQL database:

```sh
createdb -U postgres schedulerx
```

Or inside `psql`:

```sql
CREATE DATABASE schedulerx;
```

### 3. Configure Application Properties

Modify [`application.yml`](src/main/resources/application.yml) to match your database credentials:

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/schedulerx
    username: postgres
    password: postgres
```

### 4. Build and Run

Ensure Maven is installed on your system. Then, build the application:

```sh
mvn clean install
```

Then, you can run the application in either of the following ways:

- From the main class:

```sh
mvn spring-boot:run
```

- Or directly from the application by running the main class [`SchedulerX.java`](src/main/java/com/schedulerx/SchedulerX.java) in your IDE.
- ### 6. API Testing
  A **Postman collection** is included for easy API testing:  
  [`schedulerx.postman_collection.json`](schedulerx.postman_collection.json)

### 7. Adding New Command Types

SchedulerX supports custom **command types** that can be scheduled and executed dynamically.

#### Existing Command Types:

The system includes **two default command types**:

| Command Type | Description                                         |
| ------------ | --------------------------------------------------- |
| `STACK`      | Executes operations on a stack based on parameters. |
| `LOGGER`     | Logs the given parameters.                          |

---

#### How to Add a New Command Type:

1. **Define the Command Type**

- Add a new entry in [`CommandType.java`](src/main/java/com/schedulerx/models/CommandType.java).

2. **Implement the Command Interface**

- Create a new implementation for [`Command.java`](src/main/java/com/schedulerx/command/Command.java).

3. **Register the New Command**

- Ensure it is correctly initialized in [`CommandFactory.java`](src/main/java/com/schedulerx/utils/CommandFactory.java).

## License

This project is created and maintained by Vikash Singh. It is not licensed under any open-source license, but you are free to use, modify, and distribute it with proper attribution.
