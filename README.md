# Food Ordering API  
This is the source code for the API backend of a food ordering application. The project provides functionalities for managing menus, orders, and user authentication.

## Features

- User Authentication: User registration, login, logout. 
- Menu Management: Add, update, delete, and view menus.  
- Cart Management: Add, update, delete items in cart. 
- Role Management: Separate roles for customers and admins.  

## Requirements

- Java: 17  
- Maven: 3.9+ 
- MySQL: Running on default port (3306)  

## Installation

1. Clone the repository:  
    ```
    git clone https://github.com/TxZer0/Spring_Backend_Food_Ordering.git
    cd Spring_Backend_Food_Ordering
    ```

2. Update `application.yaml`:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/food_ordering
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3. Install dependencies:  
    ```
    mvn clean install
    ```

4. Start the development server:  
    ```
    mvn spring-boot:run
    ```


### Contact:
  Feel free to reach out with any questions or feedback!  
  Email: laithanhtaeh190@gmail.com
