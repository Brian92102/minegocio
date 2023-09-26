drop table customer_address;
drop table client;

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    identification_type VARCHAR(6) NOT NULL,
    identification_number VARCHAR(15) UNIQUE NOT NULL,
    names VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    phone_number VARCHAR(20)
);

CREATE TABLE customer_address (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES client(id),
    province VARCHAR(50),
    city VARCHAR(50),
    address VARCHAR(100),
    is_primary BOOLEAN DEFAULT FALSE
);
