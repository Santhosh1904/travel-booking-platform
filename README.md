# Travel Booking Platform

A console-based Travel Booking Platform developed using Java and MySQL.

## Features

- User Registration
- User Login and Logout
- View Travel Destinations
- Book a Trip
- Automatic Logged-in User Handling
- Payment Processing
- View My Bookings
- Cancel Booking
- Booking Status Management
- Payment Refund on Cancellation
- Input Validation and Error Handling
- MySQL Database Integration

## Technologies Used

- Java 17
- MySQL 8.0
- JDBC
- VS Code

## Database

Database Name:

travel_booking

Main Tables:

- users
- destinations
- bookings
- payments

## Project Structure

travel-booking-platform/
│
├── src/
│   ├── Main.java
│   ├── Register.java
│   ├── Login.java
│   ├── Destinations.java
│   ├── BookTrip.java
│   ├── Payment.java
│   ├── ViewBookings.java
│   ├── CancelBooking.java
│   ├── DatabaseConnection.java
│   └── TravelBookingApp.java
│
├── sql/
│   └── database.sql
│
├── lib/
│   └── mysql-connector-j.jar
│
├── docs/
│
├── .gitignore
└── README.md

## How to Run

1. Install Java 17.
2. Install MySQL 8.0.
3. Create the `travel_booking` database.
4. Run the SQL script from `sql/database.sql`.
5. Configure the `TRAVEL_DB_PASSWORD` environment variable with your MySQL password.
6. Add the MySQL Connector/J `.jar` file to the `lib` folder.
7. Compile and run `TravelBookingApp.java` from VS Code.

## Security

- Database passwords are not stored directly in the source code.
- The MySQL password is read from the `TRAVEL_DB_PASSWORD` environment variable.
- Sensitive credentials should never be committed to GitHub.