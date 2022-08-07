DROP TABLE IF EXISTS product_spring;
CREATE TABLE product_spring
(
    id          serial PRIMARY KEY,
    name        VARCHAR(150),
    price       double precision,
    description VARCHAR(255)
);