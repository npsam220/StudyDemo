CREATE TABLE cart_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cart_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (id),
  CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT uk_cart_product UNIQUE (cart_id, product_id),
  CONSTRAINT chk_cart_item_quantity CHECK (quantity >= 1)
);
