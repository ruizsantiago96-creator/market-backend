-- Run once on a database created with numeric clientes.id.
-- Existing numeric IDs retain their value as strings.
BEGIN;

ALTER TABLE clientes
    ALTER COLUMN id TYPE varchar(255) USING id::varchar(255);

ALTER TABLE compras
    ADD CONSTRAINT fk_compras_cliente
    FOREIGN KEY (id_cliente) REFERENCES clientes (id);

COMMIT;
