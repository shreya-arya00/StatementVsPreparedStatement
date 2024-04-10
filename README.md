# StatementVsPreparedStatement

# Task 2

## Practical Example of SQL Injection

### Prerequisites
Before running the Java program, ensure you have the following:
- Java Development Kit (JDK) installed
- PostgreSQL installed or running in a Docker container

### Running PostgreSQL in Docker
To run a Docker container for PostgreSQL, use the following command:
docker run --name my-postgres-container -e POSTGRES_PASSWORD=mysecretpassword -d postgres


### Java JDBC Example with Prepared Statement
This Java application demonstrates the use of JDBC (Java Database Connectivity) with a PostgreSQL database, focusing on preventing SQL injection by using prepared statements.

#### Environment Setup
1. Compile the Java File:
javac JdbcFirstExample.java

2. Run the Java Program:
java -classpath .:postgresql-42.7.3.jar JdbcFirstExample

Ensure to pass the PostgreSQL JDBC driver (postgresql-42.7.3.jar) at runtime.

#### Database Schema and Data
Consider a dummy table named `ValueTable` with columns `Category` and `Value`. Below is a sample schema and data:

| Category       | Value  |
| -------------- | ------ |
| Category1      | Value1 |
| Category1      | Value2 |
| ...            | ...    |
| CategoryAdmin  | VeryImp|

#### SQL Injection Scenario
- Using Statement (Vulnerable): The application prompts the user to filter the table by Category. The SQL query is constructed as follows:
"SELECT * FROM ValueTable WHERE Category = '" + category + "'"

If the user manipulates the input by sending category as Category3' or 1=1--, it would result in:
SELECT * FROM ValueTable WHERE Category = 'Category3' or 1=1--

This could potentially return all values, including those restricted for admin.

- Using Prepared Statement (Safe): Alternatively, the application uses a prepared statement with a parameterized query:
"SELECT * FROM ValueTable WHERE Category = ?"

When executed, this method prevents SQL injection attempts, ensuring that unexpected input like Category3' or 1=1-- is treated as a literal value for Category, hence preventing any injection.
