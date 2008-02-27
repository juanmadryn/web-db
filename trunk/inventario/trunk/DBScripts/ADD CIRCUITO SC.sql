-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0006", "Solicitudes de compra", "Circuito para la gestión de solicitudes de compra", NULL);

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;