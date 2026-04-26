CREATE TABLE IF NOT EXISTS products (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(20)    NOT NULL,
    description   VARCHAR(100),
    stock         INT            NOT NULL,
    price         DECIMAL(10,2)  NOT NULL,
    rating        DECIMAL(3,2),
    condition ENUM('NOU','BON_ESTAT','ACCEPTABLE','MAL_ESTAT'),
    status        BOOLEAN,
    data_created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_updated  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

-- Tabla de Usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    data_created DATETIME,
    data_updated DATETIME
);

-- Tabla Pivot N:M
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tabla Customers (1:1 con User)
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    status BOOLEAN DEFAULT TRUE,
    data_created DATETIME,
    data_updated DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabla Address (1:N con Customer)
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    address VARCHAR(255),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    is_default BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Tabla Orders
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    order_date DATETIME,
    total_amount DECIMAL(10,2),
    order_status VARCHAR(20), -- PENDENT, PROCESSAT, CANCELAT
    status BOOLEAN DEFAULT TRUE,
    data_created DATETIME,
    data_updated DATETIME,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Tabla Order Items (Detalle de la orden)
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    unit_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);