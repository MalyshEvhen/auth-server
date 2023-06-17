-- clients
CREATE TABLE clients (
    id BIGSERIAL NOT NULL,
    client_id TEXT NOT NULL,
    client_secret TEXT NOT NULL,
    access_token_ttl BIGINT,
    type TEXT
);
ALTER TABLE clients
ADD CONSTRAINT clients_pkey PRIMARY KEY (id);
-- client authorization method
CREATE TABLE client_authentication_methods (id BIGINT NOT NULL, value TEXT);
-- grant types
CREATE TABLE grant_types (id BIGINT NOT NULL, value TEXT);
-- redirect URIs
CREATE TABLE redirect_urls (id BIGINT NOT NULL, redirect_urls TEXT);
-- scopes
CREATE TABLE scopes (id BIGINT NOT NULL, scopes TEXT);
-- users
CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    email TEXT NOT NULL,
    uuid UUID NOT NULL,
    password TEXT NOT NULL,
    username TEXT NOT NULL
);
ALTER TABLE users
ADD CONSTRAINT users_pkey PRIMARY KEY (id);
-- user roles
CREATE TABLE roles (id BIGINT NOT NULL, value TEXT);