-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0008", "Ordenes de compra", "Circuito para la gestión de ordenes de compra", NULL);

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;