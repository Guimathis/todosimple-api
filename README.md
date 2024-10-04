
<h1 align="center" style="font-weight: bold;">Simple to do list 💻</h1>
<p align="center">
  <b>This project contains an implementation of one RESTful API, using the framework Spring Boot of language java. 
Is a simple application where a user can sign up, realize log in to receive an authentication token JWT (JSON Web Token) and register tasks to your account</b>
</p>


## 🚀 Getting Started

## Tecnologies
- **Frontend**:

![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/-boostrap-0D1117?style=for-the-badge&logo=bootstrap&labelColor=0D1117)

- **Backend**:

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

- **Database**:

![MySQL]( https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

- **Tools**

![IntelliJ](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

---

## ⤵ Utilization instructions

These instructions will get you up and running with a copy of the project on your local machine for testing, development, and learning purposes.
Prerequisites:
- Have installed all the tools and dependencies:
  - Google Chrome / Microsoft Edge
  - Postman: https://www.postman.com/downloads/
  - Java JDK (version >= 17)
  - Maven
  - MySQL Workbench
  - IDE
    - IntelliJ IDEA, VScode, Eclipse
    

- Step 1: Clone the repository:
  ```bash
  $ git clone https://github.com/Guimathis/todosimple-api.git
  ```

- Step 2 on linux: Enter the application.properties file:
  ```bash
  $ vi todosimple-api\src\main\resources\application-dev.properties
  ```
- Step 2 on Windows: Enter the application.properties file:
  ```bash
  $ notepad todosimple-api\src\main\resources\application-dev.properties
  ```
      or open in your preference IDE.


- Step 2.1: Configure the database credentials according to your MySQL Server installation:
  ```proprieties
  # Database config
  spring.datasource.url=jdbc:mysql://localhost:3306/todosimple?createDatabaseIfNotExist=true
  spring.datasource.username=YOUR_USERNAME
  spring.datasource.password=YOUR_PASSWORD
  ```
  
- Step 2.2: Back to the root project directory:
  ```bash
  $ cd todosimple-api\
  ```
  
- Step 2.3: Initialize the Spring Boot application:
  ```bash
  $ mvn clean install
  ```
- step 2.3.1: Initalize the spring boot application using Maven:
  ```bash
  $ mvn spring-boot:run
  ```
    - API the api will be running on http://localhost:8080/


- Step 3: Enter the application frontend after uploading the API

- Step 3.1: Enter the project root folder:
  ```bash
  $ cd todosimple-api\
  ```

- Step 3.2: Open the index.html file directly or through the VsCode Live Server extension:
  ```bash
  $ cd todosimple-api\view
  $ login.html
  ```

  - Frontend will be running on http://127.0.0.1:5500/view/login.html if initiated with Live Server.
