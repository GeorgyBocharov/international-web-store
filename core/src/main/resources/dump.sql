CREATE SCHEMA IF NOT EXISTS online_store;

DROP TABLE IF EXISTS online_store.shipments;
DROP TABLE IF EXISTS online_store.parameters;
DROP TABLE IF EXISTS online_store.parameter_types;
DROP TABLE IF EXISTS online_store.infos;
DROP TABLE IF EXISTS online_store.languages;
DROP TABLE IF EXISTS online_store.order_items;
DROP TABLE IF EXISTS online_store.products;
DROP TABLE IF EXISTS online_store.payments;
DROP TABLE IF EXISTS online_store.orders;
DROP TABLE IF EXISTS online_store.clients;
DROP TABLE IF EXISTS online_store.currencies;

CREATE TABLE online_store.clients
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    phone            VARCHAR(50),
    region           INTEGER,
    creation_date    TIMESTAMP   NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT      NOT NULL
);

CREATE TABLE online_store.currencies
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50) UNIQUE NOT NULL,
    multiplier       NUMERIC            NOT NULL DEFAULT 1,
    creation_date    TIMESTAMP          NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT             NOT NULL
);

CREATE TABLE online_store.products
(
    id               BIGSERIAL PRIMARY KEY,
    price_cu         NUMERIC   NOT NULL,
    creation_date    TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT    NOT NULL
);

CREATE TABLE online_store.languages
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50) UNIQUE NOT NULL,
    creation_date    TIMESTAMP          NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT             NOT NULL
);

CREATE TABLE online_store.infos
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(50) NOT NULL,
    description      VARCHAR(200),
    product_id       BIGINT      NOT NULL,
    language_id      BIGINT      NOT NULL,
    creation_date    TIMESTAMP   NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT      NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES online_store.products (id),
    FOREIGN KEY (language_id)
        REFERENCES online_store.languages (id)
);

CREATE TABLE online_store.orders
(
    id               BIGSERIAL PRIMARY KEY,
    currency_id      BIGINT    NOT NULL,
    client_id        BIGINT    NOT NULL,
    creation_date    TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT    NOT NULL,
    FOREIGN KEY (currency_id)
        REFERENCES online_store.currencies (id),
    FOREIGN KEY (client_id)
        REFERENCES online_store.clients (id)
);

CREATE TABLE online_store.shipments
(
    id               BIGSERIAL PRIMARY KEY,
    order_id         BIGINT       NOT NULL,
    address          varchar(100) NOT NULL,
    creation_date    TIMESTAMP    NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT       NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES online_store.orders (id)
);

CREATE TABLE online_store.order_items
(
    id               BIGSERIAL PRIMARY KEY,
    order_id         BIGINT    NOT NULL,
    product_id       BIGINT    NOT NULL,
    quantity         INT       NOT NULL DEFAULT 0,
    creation_date    TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT    NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES online_store.orders (id),
    FOREIGN KEY (product_id)
        REFERENCES online_store.products (id)
);

CREATE TABLE online_store.payments
(
    id               BIGSERIAL PRIMARY KEY,
    order_id         BIGINT      NOT NULL,
    currency_id      BIGINT      NOT NULL,
    card_pan         VARCHAR(50) NOT NULL,
    value            NUMERIC     NOT NULL DEFAULT 0,
    creation_date    TIMESTAMP   NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT      NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES online_store.orders (id),
    FOREIGN KEY (currency_id)
        REFERENCES online_store.currencies (id)
);

CREATE TABLE online_store.parameter_types
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(50) UNIQUE NOT NULL,
    creation_date    TIMESTAMP          NOT NULL,
    last_update_date TIMESTAMP,
    version          BIGINT             NOT NULL
);

CREATE TABLE online_store.parameters
(
    id                BIGSERIAL PRIMARY KEY,
    product_id        BIGINT       NOT NULL,
    parameter_type_id BIGINT       NOT NULL,
    value             VARCHAR(100) NOT NULL,
    creation_date     TIMESTAMP    NOT NULL,
    last_update_date  TIMESTAMP,
    version           BIGINT       NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES online_store.products (id),
    FOREIGN KEY (parameter_type_id)
        REFERENCES online_store.parameter_types (id)
);