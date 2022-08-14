DROP TABLE IF EXISTS product_spring;
CREATE TABLE product_spring
(
    id          integer PRIMARY KEY,
    name        VARCHAR(255),
    price       float precision,
    description VARCHAR(255)
);