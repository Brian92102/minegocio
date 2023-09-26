CREATE USER user_service WITH PASSWORD 'user_pass';

GRANT CONNECT ON DATABASE minegocio TO user_service;
GRANT USAGE ON SCHEMA "public" TO user_service;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA "public" TO user_service;

