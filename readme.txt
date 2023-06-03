This program will read instructions from a text file called "transfile.txt". There is 6 different instructions that can be called.
First it the program will make two tables called "Employee" and "Department" which will hold data from the text file. The text file
will have lines of commands with data. At the start of the line will be a number which indicates which command should be run in the program.
Instructions:
1 John
Deletes an existing employee, in this example it would be John. If John exist there will be a message saying "Delete", if the
employee was not found then a message "Not found" will show.

2 Edwards DataScience 200000 Chicago
Adds employee to the employee database. If the employee already exist then a message "Duplicate name"
will show, if not then a message "Added" will show.

3 DataScience
Deletes an existing department,in this example it would be DataScience. If DataScience exist there will be a message saying "Delete", if the
department was not found then a message "Not found" will show.

4 AI Cathy
Adds department to the department database. If the department already exists then it will delete the existing tuple, in any case a new tuple will
be inserted.

5 Mary
Gets all the employee name who work directly under the manager name given, and any employee who works indirectly under the manager

6 Cathy
Oupts all the departments managed by given manager.

Once the text file is done being read all the tables will be dropped.