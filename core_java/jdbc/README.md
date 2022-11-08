# Introduction
To have a better understanding of **JDBC**, which is standard Java API, and data access-design patterns
such as **DAO** and **Repository**, this Maven project had been implemented using containerized **PostgreSQL**. 
The application allows the users to execute **CRUD** operations (Create, Read, Update, Delete) against 
the customer table. Every operation is done in the `JDBCExecutor` class.
# Implementaiton
## ER Diagram
Below is an Entity Relationship diagram that represents the relationships between the
entities in our database
![](./assets/HPlusSport.png)

## Design Patterns
**DAO** and **Repository** patterns are different ways that we can implement the 
**Data Access Layer**, which is a type of database access logic that is used to 
keep the code organized.  

In our use case, we could say that we used a **repository** pattern as it
usually handles one type of object, meaning that it focuses on **single-table access**
per class, and we are interacting with only `customer` table in our application.  

In the **repository** pattern, we join the code instead of joining the table using the 
SQL statement when we are running the operations against the table. 
As a result, the **repository** pattern behave like a `Collection` as it supports operations 
such as **add** and **remove**. Repository pattern has strength when a complex join is 
required as it allows us to store a piece of data in a separate database, 
so we can focus on single-table access. 

Unlike a repository pattern, a **DAO** pattern does not restrict us to store data of 
the same type, so we can join multiple tables in the SQL statements.
Therefore, when we have our data highly **normalized**, the DAO pattern can be more beneficial 
because once the data is normalized, our join query will not be complex. Also, when
**atomic transactions** are required, it is much easier to use DAO than the repository.



# Test
To ensure that the application is working as expected, we have done manual testing.
We created a `DatabaseConnectionManager` that takes host, database name, username, and password to 
ensure that we successfully establish a connection with PostgreSQL. 
In CustomerDAO class, we tested several features by creating a query for CRUD, then we used 
**PreparedStatement** that has the query passed as an argument. After we set the designated 
parameter to the given value, the statements were executed.
Following is what we did to test each feature:  
**Insert a customer**:
```
// create a connection
DatabaseConnectionManager manager = new DatabaseConnectionManager("localhost",
        "hplussport", "postgres", "password");
Connection connection = manager.getConnection();
CustomerDAO customerDAO = new CustomerDAO(connection);
Customer customer = new Customer();

// ... set customer properties

customerDAO.create(customer)
```
**Check the result in the database**:
```
SELECT * FROM customer WHERE first_name='Heesoo';

-- result
 customer_id | first_name | last_name |         email          |   phone    |    address     |  city   | state | zipcode 
-------------+------------+-----------+------------------------+------------+----------------+---------+-------+---------
       10002 | Heesoo     | Lim       | heesoo.lim@example.com | 4638492734 | 3445 Yonge st. | Toronto | ON    | 12546
```

**Delete customer information**:
```
customerDAO.delete(10002);
```
**Check the result in the database**:
```
SELECT * FROM customer WHERE first_name='Heesoo';

-- result: no customer found
 customer_id | first_name | last_name |         email          |   phone    |    address     |  city   | state | zipcode 
-------------+------------+-----------+------------------------+------------+----------------+---------+-------+---------
```

