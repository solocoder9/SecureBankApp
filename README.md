# 💼 Secure Banking Application

## 🚀 Overview
The **Secure Banking Application** is a RESTful API-based banking system that enables secure user account management, fund transfers, and transaction inquiries. Built using **Spring Boot**, this application ensures smooth and secure financial operations.

## ✨ Features
- 🔐 **User Registration & Authentication** - Secure login and registration.
- 💵 **Fund Transfers** - Transfer money between accounts securely.
- 💳 **Debit & Credit Transactions** - Perform withdrawals and deposits.
- 📈 **Bank Statements** - Retrieve transaction history.
- 👤 **User Balance Inquiry** - Check account balance anytime.
- 🛡️ **Security** - Built-in authentication and secure endpoints.

## 🛠️ Tech Stack
- **Back-End:** Java, Spring Boot, Spring Security, Hibernate
- **Database:** MySQL
- **Server:** Apache Tomcat
- **Build Tool:** Maven
- **IDE:** Eclipse / IntelliJ IDEA

## 📃 API Endpoints
### 👥 User Management
- **POST** `/api/user` - Create a new user account.
- **POST** `/api/user/login` - User login authentication.

### 💸 Transactions
- **POST** `/api/user/transfer` - Transfer funds between accounts.
- **POST** `/api/user/debit` - Withdraw money from an account.
- **POST** `/api/user/credit` - Deposit money into an account.

### 📊 Inquiry
- **GET** `/api/user/nameInquiry` - Retrieve user details by name.
- **GET** `/api/user/balanceInquiry` - Check account balance.
- **GET** `/bankStatement` - View bank statement and transaction history.

## 📑 Prerequisites
Ensure you have the following installed:
- ☕ Java 8 or later - [Download Here](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- 💪 Apache Maven - [Download Here](https://maven.apache.org/download.cgi)
- 💳 MySQL Database - [Download Here](https://dev.mysql.com/downloads/)
- 💻 Eclipse IDE - [Download Here](https://www.eclipse.org/downloads/)
- 🛠️ Apache Tomcat - [Download Here](https://tomcat.apache.org/download-90.cgi)

## 🚦 Setup Instructions
1. **Clone the Repository**
   ```sh
   git clone https://github.com/solocoder9/SecureBankApp.git
   cd SecureBankApp
   ```
2. **Import Project in Eclipse**
   - Open Eclipse IDE.
   - Go to **File > Import > Existing Maven Projects**.
   - Select the cloned repository directory and click **Finish**.
3. **Configure Database**
   - Create a database in MySQL:
     ```sql
     CREATE DATABASE secure_bank;
     ```
   - Update database connection details in `application.properties`.
4. **Build and Run the Project**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
5. **Access the Application**
   - Open your browser and navigate to:
     ```
     http://localhost:8080/v3/api-docs
     ```

## 🤖 Usage
1. **Register** - Create a new user account.
2. **Login** - Authenticate and access banking features.
3. **Manage Transactions** - Deposit, withdraw, and transfer funds securely.
4. **Check Balance** - View account balance in real-time.
5. **View Statements** - Retrieve transaction history for better financial tracking.

## 📦 Dependencies
- **Spring Boot** - Framework for building Java applications.
- **Spring Security** - Authentication and authorization.
- **Hibernate** - ORM framework for database interactions.
- **MySQL Connector** - Database connection driver.
- **Swagger** - API documentation and testing.

## 🙏 Contribution Guidelines
1. **Fork the repository.**
2. **Create a new branch:**
   ```sh
   git checkout -b feature-branch
   ```
3. **Commit changes:**
   ```sh
   git commit -m "Add new feature"
   ```
4. **Push changes:**
   ```sh
   git push origin feature-branch
   ```
5. **Open a pull request.**

## 📃 License
This project is licensed under the **MIT License**. See `LICENSE` for details.

## 👤 Contact
For questions or suggestions, feel free to reach out:
- **Email:** solocoder9@gmail.com
- **GitHub:** [solocoder9](https://github.com/solocoder9)

