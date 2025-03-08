# 🔒 Secure Banking Application  
![Secure Banking App](https://img.shields.io/badge/Version-1.0-blue) ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0-green) ![Java](https://img.shields.io/badge/Java-17-orange)  

## 🚀 Overview  
Secure Banking Application is a **RESTful API-based banking system** built using **Spring Boot**. It provides essential banking functionalities such as **user account management, fund transfers, debit/credit operations, and balance inquiries** with a focus on **security and performance**.  

## 🌟 Features  
✅ User **registration & authentication** (JWT-based)  
✅ **Secure fund transfers** between accounts  
✅ **Debit & credit** operations with validation  
✅ **Balance & name inquiry** endpoints  
✅ **Transaction history retrieval**  
✅ Built using **Spring Boot, Hibernate, and MySQL**  
✅ **Swagger API Documentation** (`/swagger-ui`)  

---

## 📱 API Endpoints  
🔹 **User Management**  
- `POST /api/user` → Register a new user  
- `POST /api/user/login` → User login (JWT authentication)  

🔹 **Account Transactions**  
- `POST /api/user/transfer` → Transfer funds  
- `POST /api/user/debit` → Debit from account  
- `POST /api/user/credit` → Credit account  

🔹 **Account Inquiries**  
- `GET /api/user/nameInquiry` → Fetch user name  
- `GET /api/user/balanceInquiry` → Get account balance  
- `GET /bankStatement` → Get transaction history  

---

## 🛠 Installation & Setup  

### 1️⃣ **Clone the Repository**  
```sh
git clone https://github.com/solocoder9/SecureBankApp.git
cd SecureBankApp
```

### 2️⃣ **Set Up the Database**  
Create a **MySQL database** named `secure_bank_db` and update `application.properties`:  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/secure_bank_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3️⃣ **Build & Run the Application**  
```sh
mvn clean install
mvn spring-boot:run
```
The server will start at **http://localhost:8080**  

### 4️⃣ **Access API Documentation (Swagger UI)**  
📜 Open [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)  

---

## 🏠 Technologies Used  
- **Spring Boot 3.0** (Backend Framework)  
- **Spring Security & JWT** (Authentication & Authorization)  
- **Hibernate & JPA** (Database ORM)  
- **MySQL** (Relational Database)  
- **Swagger 3.0** (API Documentation)  
- **Lombok** (Reducing Boilerplate Code)  
- **Maven** (Build Automation)  

---

## 🖼 Recommended Screenshots  
🔹 **Swagger API Documentation** (`/swagger-ui`)  
🔹 **Database Schema (ER Diagram)**  
🔹 **Postman Requests (Login, Transfer, Balance Inquiry)**  

📌 **Add screenshots here** for better visualization!  

---

## 🔒 Security Measures  
✔ **JWT-based authentication** to secure API endpoints  
✔ **Role-based access control (RBAC)**  
✔ **Input validation & exception handling**  
✔ **Spring Security for request filtering**  

---

## 🚀 Future Enhancements  
🔹 Implement **Two-Factor Authentication (2FA)**  
🔹 Add **email/SMS notifications** for transactions  
🔹 Integrate **GraphQL API** for flexible queries  
🔹 UI Dashboard for user account management  

---

## 🤝 Contributing  
Want to improve this project? Feel free to fork, create a pull request, or raise an issue!  

🔗 **GitHub Repository**: [SecureBankApp](https://github.com/solocoder9/SecureBankApp)  

💎 **Contact**: [Subham Subhakanta Rout](mailto:your-email@example.com)  

---

🎯 **If you like this project, don't forget to ⭐ star the repository!** 🚀
