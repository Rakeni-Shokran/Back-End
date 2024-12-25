CREATE TABLE IF NOT EXISTS `USER` (
                                      id INT NOT NULL AUTO_INCREMENT,
                                      name VARCHAR(100) NOT NULL,
                                      phoneNumber VARCHAR(15) NOT NULL,
                                      password VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL,
                                      PRIMARY KEY (id),
                                      UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS DRIVER (
                                      id INT NOT NULL,
                                      payment INT NOT NULL,
                                      license VARCHAR(50) NOT NULL,
                                      PRIMARY KEY (id),
                                      FOREIGN KEY (id) REFERENCES `USER`(id),
                                      UNIQUE (license)
);

CREATE TABLE IF NOT EXISTS PARKING_MANAGER (
                                               id INT NOT NULL,
                                               PRIMARY KEY (id),
                                               FOREIGN KEY (id) REFERENCES `USER`(id)
);

CREATE TABLE IF NOT EXISTS SYS_ADMIN (
                                         id INT NOT NULL,
                                         PRIMARY KEY (id),
                                         FOREIGN KEY (id) REFERENCES `USER`(id)
);

CREATE TABLE IF NOT EXISTS PARKING_LOT (
                                           location VARCHAR(255) NOT NULL,
                                           id INT NOT NULL AUTO_INCREMENT,
                                           capacity INT NOT NULL,
                                           pricingStructure INT NOT NULL,
                                           PRIMARY KEY (id),
                                           UNIQUE (location)
);

CREATE TABLE IF NOT EXISTS PARKING_SPOT (
                                            id INT NOT NULL AUTO_INCREMENT,
                                            status INT NOT NULL,
                                            type INT NOT NULL,
                                            PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PARKING_LOT_typeOfSlots (
                                                       typeOfSlots INT NOT NULL,
                                                       id INT NOT NULL,
                                                       PRIMARY KEY (typeOfSlots, id),
                                                       FOREIGN KEY (id) REFERENCES PARKING_LOT(id)
);
