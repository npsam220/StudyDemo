CREATE TABLE product (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  product_code VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  price INT,
  stock INT,
  CONSTRAINT uk_product_code UNIQUE (product_code)
);