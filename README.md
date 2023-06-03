<a name="readme-top"></a>

<p align="center">
  <img src="https://raw.githubusercontent.com/AlexisRodriguezCS/DBTransactions/master/images/HD.jpg" alt="Grid" style="display:block;margin:auto;" height="500">
</p>
<h1 align="center">Database Transactions</h1>

<!-- TABLE OF CONTENTS -->
<p align="center">
  <a href="#about">About The Project</a> •
  <a href="#install">How To Install and Run</a> •
  <a href="#use">How To Use</a> •
  <a href="#contact">Contact</a>
</p>

<!-- ABOUT -->

<a name="about"></a>

# About This Project

The DBTransactions project is a Java application that demonstrates basic database transactions using JDBC (Java Database Connectivity). It provides functionalities to perform various operations on an employee and department database, such as adding employees, deleting employees, managing departments, and retrieving employee information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a name="install"></a>

<!-- HOW TO INSTALL AND RUN -->

# How to Install and Run

To install and run the DBTransactions project, follow these steps:

1. Clone the repository to your local machine by running the following command in your terminal or command prompt:

   `git clone https://github.com/your-username/DBTransactions.git`

2. Ensure you have Java Development Kit (JDK) installed on your machine. You can download and install the latest JDK from the official Oracle website: [Java SE Downloads](https://www.oracle.com/java/technologies/downloads/#java11)

3. Install MySQL Workbench, which provides a visual interface for managing MySQL databases. You can download MySQL Workbench from the official MySQL website: [MySQL Workbench Downloads](https://dev.mysql.com/downloads/workbench/)

4. Open the project directory in your preferred Java IDE.

5. Two Different ways to run the application.

   a. Compile and Run via Command Line:

   - Open a terminal or command prompt.
   - Navigate to the project directory.
   - Compile the Java source files using the following command:

     `javac -cp . com/example/DBTransactions/DBTransactions.java`

   - Run the application using the following command:

     `java -cp . com.example.DBTransactions.DBTransactions`

   b. Run via IDE:

   - Open your Java IDE (such as IntelliJ IDEA, Eclipse, or NetBeans).
   - Import the project into the IDE.
   - Locate the DBTransactions.java file in the project structure.
   - Right-click on the file and select "Run" or "Run DBTransactions.main()".

6. The application will prompt you to enter the database URL, username, and password. Provide the necessary information to establish a connection with the database.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a name="use"></a>

<!-- HOW TO USE -->

# How to Use

Once the DBTransactions application is running, it reads the transfile.txt file located in the transaction_files directory. The transfile.txt file contains a series of transaction codes and associated data, specifying various operations to be performed on the database.

The transaction codes and their corresponding operations are as follows:

- Transaction Code 1: Delete an existing employee.
- Transaction Code 2: Add a new employee.
- Transaction Code 3: Delete a department and update associated employees.
- Transaction Code 4: Add a new department and update associated employees.
- Transaction Code 5: Output all employees working under a given manager.
- Transaction Code 6: Output all departments managed by a given manager.

The transfile.txt file should be structured with each line representing a separate transaction, starting with the transaction code followed by relevant data.

Example transfile.txt:

`2 John Doe IT 5000 New York`

`1 Jane Smith`

`5 John Doe`

After executing the transactions from the transfile.txt, the application will drop the created tables and close the database connection.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

<a name="contact"></a>

# Contact

Alexis Rodriguez - [LinkedIn](https://www.linkedin.com/in/alexisrodriguezcs/) - alexisrodriguezdev@gmail.com

Project Link: [https://github.com/AlexisRodriguezCS/DBTransactions](https://github.com/AlexisRodriguezCS/DBTransactions)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
