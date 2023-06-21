-- users
CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    email TEXT NOT NULL,
    uuid UUID NOT NULL,
    password TEXT NOT NULL,
    username TEXT NOT NULL,
    updated_at TIMESTAMP,
    created_at TIMESTAMP,
);
ALTER TABLE users
ADD CONSTRAINT users_pkey PRIMARY KEY (id);
-- user roles
CREATE TABLE roles (id BIGINT NOT NULL, value TEXT);