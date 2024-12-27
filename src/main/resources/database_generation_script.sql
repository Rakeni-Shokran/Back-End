CREATE TABLE IF NOT EXISTS USER (
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    name VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('DRIVER', 'LOT_MANAGER', 'ADMIN') DEFAULT 'DRIVER',
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS DRIVER (
    id BIGINT NOT NULL UNIQUE ,
    payment VARCHAR(120) NOT NULL,
    license VARCHAR(222) NOT NULL UNIQUE ,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS PARKING_MANAGER(
    id BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES USER(id)
);
CREATE TABLE IF NOT EXISTS UNAPPROVED_PARKING_MANAGER(
    id BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS SYS_ADMIN(
    id BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS PARKING_LOT(
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    location VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    pricingStructure INT NOT NULL,
    parkingManagerId BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (parkingManagerId) REFERENCES PARKING_MANAGER(id)
);

CREATE TABLE IF NOT EXISTS PARKING_SPOT(
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    status VARCHAR(10) NOT NULL, -- Available, Occupied
    type VARCHAR(10) NOT NULL, -- Regular, Disabled, Electric
    parkingLotId BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (parkingLotId) REFERENCES PARKING_LOT(id)
);

CREATE TABLE IF NOT EXISTS RESERVATION(
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    startTimeStamp TIMESTAMP NOT NULL,
    endTimeStamp TIMESTAMP NOT NULL,
    price INT NOT NULL,
    userId BIGINT NOT NULL,
    parkingSpotId BIGINT NOT NULL,
    isReminded TINYINT NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES DRIVER(id),
    FOREIGN KEY (parkingSpotId) REFERENCES PARKING_SPOT(id)
);

CREATE TABLE IF NOT EXISTS PENALTY(
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    fees INT NOT NULL,
    type VARCHAR(20) NOT NULL, -- Overstay, NotShowingUp
    userId BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES DRIVER(id)
);

CREATE TABLE IF NOT EXISTS NOTIFICATION(
    id BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    messageType VARCHAR(255) NOT NULL,
    seen TINYINT NOT NULL,
    notificationTimeStamp TIMESTAMP NOT NULL,
    userId BIGINT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES USER(id)
);

CREATE TABLE IF NOT EXISTS TYPE_OF_SPOTS_IN_LOT(
    typeOfSpots VARCHAR(10) NOT NULL,
    parkingLotId BIGINT NOT NULL,
    PRIMARY KEY (typeOfSpots, parkingLotId),
    FOREIGN KEY (parkingLotId) REFERENCES PARKING_LOT(id)

);
#
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('John Doe', '1234567890', 'john.doe@example.com', 'password123', 'DRIVER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Jane Smith', '0987654321', 'jane.smith@example.com', 'password123', 'DRIVER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Alice Johnson', '1112223333', 'alice.johnson@example.com', 'password123', 'DRIVER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Bob Brown', '4445556666', 'bob.brown@example.com', 'password123', 'DRIVER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Charlie Davis', '7778889999', 'charlie.davis@example.com', 'password123', 'DRIVER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('David Wilson', '2223334444', 'david.wilson@example.com', 'password123', 'LOT_MANAGER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Eve White', '5556667777', 'eve.white@example.com', 'password123', 'LOT_MANAGER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Frank Green', '8889990000', 'frank.green@example.com', 'password123', 'LOT_MANAGER');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Grace Black', '3334445555', 'grace.black@example.com', 'password123', 'ADMIN');
# INSERT INTO USER (name, phoneNumber, email, password, role) VALUES ('Hank Blue', '6667778888', 'hank.blue@example.com', 'password123', 'ADMIN');
# INSERT INTO DRIVER (id, payment, license) VALUES (1, 'Credit Card', 'D1234567');
# INSERT INTO DRIVER (id, payment, license) VALUES (2, 'Credit Card', 'D2345678');
# INSERT INTO DRIVER (id, payment, license) VALUES (3, 'Credit Card', 'D3456789');
# INSERT INTO DRIVER (id, payment, license) VALUES (4, 'Credit Card', 'D4567890');
# INSERT INTO DRIVER (id, payment, license) VALUES (5, 'Credit Card', 'D5678901');
# INSERT INTO SYS_ADMIN (id) VALUES (9);
# INSERT INTO SYS_ADMIN (id) VALUES (10);
# INSERT INTO PARKING_MANAGER (id) VALUES (6);
# INSERT INTO PARKING_MANAGER (id) VALUES (7);
# INSERT INTO PARKING_MANAGER (id) VALUES (8);

-- Mock data for PARKING_LOT table
# INSERT INTO PARKING_LOT (id, location, name, capacity, pricingStructure, parkingManagerId) VALUES
#                                                                                                (1, 'Downtown', 'Lot A', 100, 1, 3),
#                                                                                                (2, 'Uptown', 'Lot B', 150, 2, 3),
#                                                                                                (3, 'Suburbs', 'Lot C', 200, 3, 3);
#
# -- Mock data for PARKING_SPOT table
# INSERT INTO PARKING_SPOT (id, status, type, parkingLotId) VALUES
#                                                               (1, 'AVAILABLE', 'Regular', 1),
#                                                               (2, 'OCCUPIED', 'Disabled', 1),
#                                                               (3, 'AVAILABLE', 'Electric', 2),
#                                                               (4, 'AVAILABLE', 'Regular', 2),
#                                                               (5, 'OCCUPIED', 'Electric', 3);

