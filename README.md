💻 HamroCart – JSP/Servlet-Based E-Commerce Platform
HamroCart is a simple and efficient e-commerce platform developed using JSP and Servlets. Designed to deliver a smooth user experience, HamroCart allows customers to browse products, manage a shopping cart, and place orders, all powered by Java and MySQL.

🚀 Features
🛒 E-commerce Storefront
- Product listings by category
- Product details page
- Add to Cart and update quantities
- Cart summary and total price calculation

🔐 User Authentication
- User registration, login, and logout
- Access control for protected pages like Cart and Orders

📦 Admin Panel
- Manage products and categories (via database or admin section)
- View and manage user orders

🎨 Simple Responsive Frontend
- User interface built using HTML and CSS
- Clean and structured layout without JavaScript dependencies

🛠️ Tech Stack
- Backend: Java (JSP & Servlets)
- Frontend: HTML, CSS
- Database: MySQL
- Server: Apache Tomcat

⚙️ Installation & Setup
1. Clone the Repository
``` bash
git clone https://github.com/nischal-0016/HamroCart.git
cd HamroCart
```
2. Set Up the Database
- Create a new database in MySQL (e.g., hamrocart_db)
- Import the provided SQL file (database.sql) to initialize the schema and sample data

3. Configure Database Connection
- Locate DBConnection.java (commonly in the util or dao package)
- Update the database URL, username, and password according to your local MySQL setup

4. Deploy the Project
- Import the project into your Java IDE (Eclipse, IntelliJ IDEA, etc.)
- Configure the project to run on Apache Tomcat
- Build and deploy the application

5. Run the Server
- Start Apache Tomcat and access the application by visiting:
```bash
http://localhost:8080/HamroCart/
```
