/* Your scheme modifications here */

create type status_t as enum('validated', 'error', 'pending');
create type error_t as enum('database', 'network', 'other');

CREATE TABLE transactions ( 
	transaction_id serial PRIMARY KEY,
	payment_id INTEGER REFERENCES payments(payment_id) ON DELETE RESTRICT,
	error_id INTEGER REFERENCES errors(error_id) ON DELETE RESTRICT,
	status: status_t, 
	created_at: TIMESTAMP DEFAULT NOW()
);

CREATE TABLE errors (
	error_id serial PRIMARY KEY,
	error error_t,
	error_description VARCHAR (100)
);

CREATE TABLE carts (
	cart_id serial PRIMARY KEY,
	payment_id INTEGER REFERENCES payments(payment_id) ON DELETE RESTRICT
);

CREATE TABLE product_carts (
	cart_id INTEGER REFERENCES carts(cart_id) ON DELETE RESTRICT,
	product_id INTEGER REFERENCES products(product_id) ON DELETE RESTRICT,
	product_amout INTEGER  
);

CREATE TABLE products ( 
	product_id serial PRIMARY KEY,
	prize decimal,
	description VARCHAR (150),
	type VARCHAR (100),
	created_at TIMESTAMP DEFAULT NOW()
);