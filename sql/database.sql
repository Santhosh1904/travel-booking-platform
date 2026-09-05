CREATE DATABASE IF NOT EXISTS travel_booking;

USE travel_booking;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS destinations (
    destination_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    destination_id INT NOT NULL,
    booking_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (destination_id) REFERENCES destinations(destination_id)
);

CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    payment_date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

INSERT INTO destinations (name, country, description, price)
VALUES
('Goa', 'India', 'Beautiful beaches and relaxing holidays', 5000.00),
('Manali', 'India', 'Mountain destination with scenic views', 7000.00),
('Dubai', 'UAE', 'Luxury shopping and desert experiences', 25000.00),
('Paris', 'France', 'Romantic city with famous landmarks', 50000.00);