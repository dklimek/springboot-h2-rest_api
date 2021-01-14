DROP TABLE IF EXISTS CARS;

CREATE TABLE CARS
(
  id VARCHAR
  AUTO_INCREMENT PRIMARY KEY,
  mark VARCHAR,
  colour VARCHAR,
  yearOfProduction VARCHAR,
  peopleCapacity VARCHAR,
  numberOfDoors VARCHAR,
  gearbox VARCHAR,
  rented BOOLEAN
  (250) NOT NULL,

);