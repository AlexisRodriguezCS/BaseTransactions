/***************************************************************
 * File: DBTransactions.java
 * Purpose: Implements database transactions for managing employees and departments.
 *          Reads transactions from a file and performs actions based on the transaction code.
 * Author: Alexis Rodriguez
 ***************************************************************/

package com.example.DBTransactions;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class DBTransactions {

    // This method creates the tables at the start of the program along with assigning the primary key(s)
    // and foreign key(s) for each table (Step 1)
    public static void createTables(Statement statement) throws SQLException {
        String employee = "CREATE TABLE employee " +
                " (ename VARCHAR(255), " +      // name of employee
                " dept_name VARCHAR(255), " +   // department to which the employee belongs
                " salary VARCHAR(255), " +      // salary of employee
                " city VARCHAR(255), " +        // city where employee lives
                " PRIMARY KEY ( ename ))";      // primary key is the name of the employee

        String department = "CREATE TABLE department " +
                " (dept_name VARCHAR(255), " +                          // department to which the manager belongs
                " mname VARCHAR(255), " +                               // name of manager
                " PRIMARY KEY ( dept_name ), " +                         // primary key is the name of the department
                " FOREIGN KEY (mname) REFERENCES EMPLOYEE (ename))";    // foreign key is manager name which can
        // also be an employee

        // Updates the database with two new tables(employee, department)
        statement.executeUpdate(employee);
        statement.executeUpdate(department);
    }

    // This method drops the tables at the end of the program (Step 4)
    public static void dropTables(Statement statement) throws SQLException {
        String employee = "DROP TABLE employee";
        String department = "DROP TABLE department";
        statement.executeUpdate(department);
        statement.executeUpdate(employee);
    }

    // This method delete an existing employee. If the employee doesn't exist then "Not found" will be shown
    // else we will delete the employee and update mname to null in department table (Step 2 a)
    public static void step2_a(String line, Connection connection) throws SQLException {
        String[] arr = line.split(" ");  // Splits the line to get the name
        String ename = "\'" + arr[1] + "\'";
        String deleteEmployee = "Delete from employee where ename = " + ename;
        PreparedStatement preparedStatement = connection.prepareStatement(deleteEmployee);
        String updateDepartment = "Update department set mname = null where mname = ?";
        int rows = preparedStatement.executeUpdate(deleteEmployee);
        if (rows == 0) {
            // Checks if
            System.out.println("Not found");
        } else {
            // If there was an employee then update department table too
            PreparedStatement preparedStatement2 = connection.prepareStatement(updateDepartment);
            preparedStatement2.setString(1, arr[1]);
            preparedStatement2.executeUpdate();
            preparedStatement.close();
            System.out.println("Deleted");
        }
    }

    // This method will add a new employee with ename,dept_name,salary, and city, if the employee already
    // exist then a message "Duplicate name" will show, else "Added" will show (Step 2 b)
    public static void step2_b(String line, Statement statement, Connection connection) throws SQLException {
        String[] arr = line.split(" ");  // Splits the line to get the ename, dept-name, salary, and city
        String query = "SELECT ename FROM employee " + "WHERE ename = '" + arr[1] + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            System.out.println("Duplicate name");
        } else {
            String newEmployee = "INSERT INTO employee (ename, dept_name , salary , city) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(newEmployee);
            preparedStatement.setString(1, arr[1]);
            //System.out.println(preparedStatement);
            preparedStatement.setString(2, arr[2]);
            preparedStatement.setString(3, arr[3]);
            preparedStatement.setString(4, arr[4]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Added");
        }
    }

    // This method will delete a tuple from the department relation and set that dept-name to null in employee relation.
    // If the tuple is found then a message "Deleted" will show, else an error message "Not found" will show (Step 3 c)
    public static void step2_c(String line, Statement statement, Connection connection) throws SQLException {
        String[] arr = line.split(" ");
        String department_name = "\'" + arr[1] + "\'";
        String deleteDepartment = "Delete from department where dept_name = " + department_name;
        PreparedStatement preparedStatementD = connection.prepareStatement(deleteDepartment);
        int rows = preparedStatementD.executeUpdate(deleteDepartment);
        if (rows == 0) {
            System.out.println("Not found");
        } else {
            String updateEmployee = "Update employee set dept_name = null where dept_name = ?";
            PreparedStatement preparedStatementE = connection.prepareStatement(updateEmployee);
            preparedStatementE.setString(1, arr[1]);
            //System.out.println(preparedStatementE);
            preparedStatementE.executeUpdate();
            preparedStatementE.close();
            System.out.println("Deleted");
        }
    }

    // This method will insert a tuple(department name, manager name) into the department relation. First checks if
    // there is an employee relation for this manager. If there is already a tuple for such department then it gets
    // delete, and insert a new one (Step 4 c)
    public static void step2_d(String line, Statement statement, Connection connection) throws SQLException {
        String[] arr = line.split(" ");
        String employeeQuery = "SELECT ename FROM employee " + "WHERE ename = '" + arr[2] + "'";
        ResultSet resultSetE = statement.executeQuery(employeeQuery);
        if (resultSetE.next()) {
            String departmentQuery = "SELECT dept_name FROM department " + "WHERE mname = '" + arr[1] + "'";
            ResultSet resultSetD = statement.executeQuery(departmentQuery);
            if (resultSetD.next()) {
                // Delete department if there is an existing tuple for such department
                String department_name = "\'" + arr[1] + "\'";
                String deleteDepartment = "Delete from department where dept_name = " + department_name;
                PreparedStatement preparedStatementD = connection.prepareStatement(deleteDepartment);
                preparedStatementD.executeUpdate(deleteDepartment);
            }
            // Add the department
            String newDepartment = "INSERT INTO department (dept_name, mname) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(newDepartment);
            preparedStatement.setString(1, arr[1]);
            preparedStatement.setString(2, arr[2]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Added");
        }
    }

    // This method will output all the employees who work indirectly, directly under the given manager name (Step 5 e)
    public static void step2_e(String line, Statement statement) throws SQLException {
        String[] arr = line.split(" ");
        String managerName = arr[1];

        // Query to get all employees who work directly or indirectly under the given manager
        String employeeList = "WITH RECURSIVE subordinates AS (" +
                "SELECT ename FROM employee WHERE ename = '" + managerName + "' " +
                "UNION " +
                "SELECT e.ename " +
                "FROM employee e " +
                "JOIN department d ON e.dept_name = d.dept_name " +
                "JOIN subordinates s ON d.mname = s.ename" +
                ") SELECT ename FROM subordinates WHERE ename != '" + managerName + "'";

        ResultSet resultSetE = statement.executeQuery(employeeList);

        if (!resultSetE.next()) {
            // If there are no employees found
            System.out.println("No employees found under the given manager");
        } else {
            // Output all the employees
            do {
                String employeeName = resultSetE.getString("ename");
                System.out.println(employeeName);
            } while (resultSetE.next());
        }
    }

    public static void step2_f(String line, Statement statement) throws SQLException {
        String[] arr = line.split(" ");
        String managerName = arr[1];

        // Query to get all the departments managed by the given manager
        String departmentQuery = "SELECT dept_name FROM department WHERE mname = '" + managerName + "'";
        ResultSet resultSetD = statement.executeQuery(departmentQuery);

        if (!resultSetD.next()) {
            // If there are no departments managed by the given manager
            System.out.println("No departments found for the given manager");
        } else {
            // Output all the departments
            do {
                String departmentName = resultSetD.getString("dept_name");
                System.out.println(departmentName);
            } while (resultSetD.next());
        }
    }


    // This method will read the file located in directory named by my netid, and has a switch
    // statement for step 2 depending on the transaction code of each line (a - f steps)
    public static void readFile(Statement statement, Connection connection) throws FileNotFoundException, SQLException {
        File file = new File("transaction_files/transfile.txt");
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {        // Reads the file line by line
            String line = myReader.nextLine();  // Holds the current line
            switch (line.charAt(0)) {
                case '1' -> // Step 2 a
                        step2_a(line, connection);
                case '2' -> // Step 2 b
                        step2_b(line, statement, connection);
                case '3' -> // Step 2 c
                        step2_c(line, statement, connection);
                case '4' -> // Step 2 d
                        step2_d(line, statement, connection);
                case '5' -> // Step 2 e
                        step2_e(line, statement);
                case '6' -> // Step 2 f
                        step2_f(line, statement);
                default -> System.out.println("Error no transaction code");
            }
        }
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the database URL: ");
        String url = scanner.nextLine();

        System.out.print("Enter the database username: ");
        String username = scanner.nextLine();

        System.out.print("Enter the database password: ");
        String password = scanner.nextLine();

        // Makes the connection to the database
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        // creates the employee and department tables (Step 1)
        createTables(statement);

        // reads the transfile.txt (Step 2)
        readFile(statement, connection);

        // drops the employee and department tables (Step 4)
        dropTables(statement);

        // Close the scanner and database connection
        scanner.close();
        statement.close();
        connection.close();
    }
}
