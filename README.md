# Pivot's Cake Shop Ordering System

## Description
This project is a console-based cake ordering system built with Java and MySQL.  
Our goal is to establish a systematic database-driven application that allows both customers and administrators to interact efficiently.
- Customers can place cake orders, modify or cancel them, and check order history.
- Administrators can manage cake inventory, view sales, and handle customer orders.

## Prerequisites
Before running this application, ensure the following are installed and ready:
1. Java Development Kit (JDK)
2. MySQL Database Server
3. MySQL JDBC Driver (Connector/J)
4. Active MySQL database connection

## Development & Runtime Environment
- **Java version used:** OpenJDK 23.0.1 (javac 23.0.1)
- **IDE:** IntelliJ IDEA
- **Build Artifact:** Executable `.jar` file (`CakeOrder.jar`) located in `/out/artifacts/CakeOrder_jar/`
- **Note:** This `.jar` was built using JDK 23. If running on older versions (e.g., JDK 17 or 11), compatibility issues may arise.

## How to Execute

### Option 1: Run Source Code
1. Save the provided Java code as `Main.java`.
2. Add the MySQL JDBC driver to your project (ensure it's in your classpath).
3. Compile the code using:
    ```bash
    javac Main.java
    ```
4. Run the program:
    ```bash
    java Main
    ```

### Option 2: Run Compiled JAR
If you want to run the pre-built executable file:
1. Make sure you have Java 23 or higher installed.
2. Open terminal or command prompt where `CakeOrder.jar` is located.
3. Run the file with:
    ```bash
    java -jar CakeOrder.jar
    ```

## Database Connection
This application connects to the database using the following credentials:
- **Host:** www.i-kina.co.kr  
- **Port:** 23406  
- **User name:** ewha  
- **Schema:** ewha  
- **Password:** ewhaPass2025##

> ⚠️ Ensure your firewall or network allows external access to this host and port.

## Usage Instructions
Once you've run the application successfully, you can interact with program through the console.  
1. **Main Menu**: The application will first display the main menu. Enter the number which corresponds to your desired action and press Enter.  
2. **Sub-menus**: Additional options for specific tasks will be shown (e.g., adding, modifying, or deleting cake information). Navigate by entering corresponding numbers.  
3. **Exit**: You can exit the application at any time by selecting the `0` option from the menu.

## Notes
- This project includes both source code and an executable `.jar`.
- For testing or grading purposes, please ensure that:
  - Java 23+ is available on the system.
  - Database access is available from your network.
- Additionally, a zipped version of OpenJDK 24 (`jdk-24.0.1_windows-x64_bin.zip`) is included for your convenience.  
  You may extract and use it to avoid compatibility issues when running the `.jar` file.
- This submission includes two types of source packaging for your convenience:
  - `PivotJavaSource.zip`: All `.java` source files are collected and organized in a flat structure, regardless of their original directory.
  - `CakeOrder.zip`: Contains the full project directory structure as maintained in the IntelliJ project (with original package paths preserved).
